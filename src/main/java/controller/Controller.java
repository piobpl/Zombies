package controller;

import model.Board;
import model.Card;
import model.Card.CardType;
import model.Deck;
import model.GameState;
import model.Hand;
import model.Modifier.ModifierType;
import model.MoveMaker;
import model.Player;
import view.EventReceiver.ButtonClickedEvent;
import view.EventReceiver.Event;
import view.EventReceiver.EventType;
import view.EventReceiver.HandClickedEvent;
import view.GUI;
import view.GUI.Button;

public class Controller {

	public final GameState gameState;
	public final GUI gui;
	public final Selector selector;
	private int stage;

	public Controller() {
		System.err.println("Creating Controller...");
		gui = new GUI();
		gameState = new GameState(gui);
		selector = new Selector(gui.eventReceiver, gameState);
		System.err.println("Done");
	}

	private interface Stage {
		public void perform(Player player);
	};

	private class advancingStage implements Stage {
		public void perform(Player player) {
			switch (player) {
			case ZOMBIE:
				Board board = gameState.getBoard();
				for (int x = 4; x >= 0; --x)
					for (int y = 0; y < 3; ++y)
						if (board.is(x, y, CardType.ZOMBIE))
							MoveMaker.moveForward(gameState, x, y);
				break;
			case HUMAN:
				break;
			}
		}
	}

	private class drawingStage implements Stage {
		public void perform(Player player) {
			Hand hand = gameState.getHand(player);
			Deck deck = gameState.getDeck(player);
			for (int i = 0; i < 4; ++i)
				if (hand.get(i) == null) {
					if (deck.isEmpty()) {
						if (player == Player.ZOMBIE)
							throw new GameOver(Player.HUMAN);
						else
							return;
					} else {
						hand.set(i, deck.getTopCard());
						System.err.println("Card drawn: "
								+ hand.get(i).getName());
					}
				}
		}
	}

	private class discardingStage implements Stage {
		public void perform(Player player) {
			Event event;
			Hand hand = gameState.getHand(player);
			int pos;
			if (hand.isEmpty())
				return;
			for (;;) {
				event = gui.eventReceiver.getNextEvent();
				if (event.type != EventType.HandClicked)
					continue;
				pos = ((HandClickedEvent) event).cardClicked;
				if (hand.isEmpty(pos))
					continue;
				if (((HandClickedEvent) event).player == player)
					break;
			}
			System.err.println("Discarded: " + hand.get(pos).getName());
			hand.set(pos, null);
		}
	}

	private class playingStage implements Stage {
		public void perform(Player player) {

			Event event;
			HandClickedEvent handClickedEvent;
			Hand hand = gameState.getHand(player);
			Card card;
			Selection selection;
			int limit = 4;
			if (player == Player.HUMAN
					&& gameState.getModifiers().contains(ModifierType.TERROR))
				limit = 1;
			while (true) {
				event = gui.eventReceiver.getNextEvent();
				if (event.type == EventType.ButtonClicked) {
					if (((ButtonClickedEvent) event).button == Button.EndTurn)
						break;
				} else if (event.type == EventType.HandClicked && limit > 0) {
					handClickedEvent = (HandClickedEvent) event;
					if (handClickedEvent.player != player)
						continue;
					card = hand.get(handClickedEvent.cardClicked);
					if (card == null)
						continue;
					System.err.println("Card selected for playing: "
							+ card.getName());
					gui.getHand(player).getCell(handClickedEvent.cardClicked)
							.setHighlight(true);
					if (card.getSelectionType() == null) {
						System.err.println("No selection, applying.");
						card.makeEffect(null, gameState);
						hand.remove(handClickedEvent.cardClicked);
						--limit;
					} else {
						selection = selector.getSelection(card);
						System.err.println("Received: " + selection);
						if (selection != null) {
							System.err.println("Selection received, applying.");
							card.makeEffect(selection, gameState);
							hand.remove(handClickedEvent.cardClicked);
							--limit;
						}
					}
					gui.setHighlight(false);
				}
			}
		}
	}

	public void game() {
		stage = 0;
		Stage[] stages = new Stage[4];
		stages[0] = new advancingStage();
		stages[1] = new drawingStage();
		stages[2] = new discardingStage();
		stages[3] = new playingStage();
		Player[] players = new Player[2];
		players[0] = Player.ZOMBIE;
		players[1] = Player.HUMAN;
		System.err.println("Game started");
		try {
			while (true) {
				gui.getInfoPanel().sendMessage("Tura: " + stage);
				for (Player p : players) {
					gui.getInfoPanel().sendMessage("Gra: " + p);
					for (Stage s : stages) {
						gameState.nextStage();
						s.perform(p);
					}
				}
				++stage;
			}
		} catch (GameOver gameOver) {
			System.out.println(gameOver.won + " has won!");
		}
		gui.exit();
	}

	public static void main(String args[]) {
		new Controller().game();
	}
}
