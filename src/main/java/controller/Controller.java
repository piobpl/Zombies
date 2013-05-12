package controller;

import model.Board;
import model.Deck;
import model.GameState;
import model.Hand;
import model.Player;
import view.EventReceiver.Event;
import view.EventReceiver.EventType;
import view.EventReceiver.HandClickedEvent;
import view.GUI;

public class Controller {

	public final GameState gameState;
	public final GUI gui;
	public final Selector selector;

	public Controller() {
		System.err.println("Creating Controller...");
		gameState = new GameState();
		gui = gameState.gui;
		selector = new Selector(gui.eventReceiver, gameState);
		System.err.println("Done");
	}

	@SuppressWarnings("unused")
	private Player turn = Player.ZOMBIE;

	private void advancingStage(Player player) {
		switch (player) {
		case ZOMBIE:
			Board board = gameState.getBoard();
			for (int j = 0; j < 3; ++j)
				if (board.is(4, j, "Zombie"))
					throw new GameOver(Player.ZOMBIE);
			for (int i = 3; i >= 0; --i) {
				for (int j = 0; j < 3; ++j) {
					if (board.is(i, j, "Zombie") && board.isEmpty(i + 1, j)) {
						board.set(i + 1, j, board.get(i, j));
						board.set(i, j, null);
					}
				}
			}
			break;
		case HUMAN:
			break;
		}
	}

	private void drawingStage(Player player) {
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
				}
			}
	}

	private void discardingStage(Player player) {
		if (gameState.getHand(player).isEmpty())
			return;
		Event event;
		HandClickedEvent handClickedEvent;
		for (;;) {
			event = gui.eventReceiver.getNextEvent();
			if (event.type != EventType.HandClicked)
				continue;
			handClickedEvent = (HandClickedEvent) event;
			if (handClickedEvent.player == player)
				break;
		}
		gameState.getHand(player).set(handClickedEvent.cardClicked, null);
	}

	private void playingStage(Player player) {

	}

	public void game() {
		System.err.println("Game started");
		try {
			while (true) {
				advancingStage(Player.ZOMBIE);
				drawingStage(Player.ZOMBIE);
				discardingStage(Player.ZOMBIE);
				playingStage(Player.ZOMBIE);
				advancingStage(Player.HUMAN);
				drawingStage(Player.HUMAN);
				discardingStage(Player.HUMAN);
				playingStage(Player.HUMAN);
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
