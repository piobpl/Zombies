package game.controller;

import game.model.Card;
import game.model.GameState;
import game.model.Hand;
import game.model.Player;
import game.model.Modifier.ModifierType;
import game.view.GUI;
import game.view.EventReceiver.ButtonClickedEvent;
import game.view.EventReceiver.Event;
import game.view.EventReceiver.EventType;
import game.view.EventReceiver.HandClickedEvent;
import game.view.GUI.Button;

import java.awt.event.MouseEvent;

public class PlayingStage implements Stage {
	public final GameState gameState;
	public final GUI gui;
	private Selector selector;

	public PlayingStage(GameState gameState, GUI gui) {
		this.gameState = gameState;
		this.gui = gui;
		selector = new Selector(gameState, gui);
	}

	public void perform(Player player) {
		Event event;
		HandClickedEvent handClickedEvent;
		Hand hand = gameState.getHand(player);
		Card card;
		Selection selection;
		int limit = 4;
		boolean endWarning = false;
		if (player == Player.HUMAN
				&& gameState.getModifiers().contains(ModifierType.TERROR))
			limit = 1;
		gui.getInfoPanel().sendMessage("Choose cards to play or end turn.");
		while (true) {
			if ((limit == 0 || gameState.getHand(player).isEmpty())
					&& !endWarning) {
				gui.getInfoPanel().sendMessage("You have to end turn now.");
				endWarning = true;
			}
			event = gui.eventReceiver.getNextEvent();
			if (event.mouseButtonId != MouseEvent.BUTTON1)
				continue;
			if (event.type == EventType.ButtonClicked) {
				if (((ButtonClickedEvent) event).button == Button.EndTurn)
					break;
			} else if (event.type == EventType.HandClicked && limit > 0) {
				handClickedEvent = (HandClickedEvent) event;
				if (handClickedEvent.player != player)
					continue;
				card = hand.get(handClickedEvent.cardClicked);
				if (card == null) {
					continue;
				} else {
					System.err.println("Card selected for playing: "
							+ card.getName());
					gui.getHand(player).getCell(handClickedEvent.cardClicked)
							.setHighlight(true);
					/*
					 * if (card.getSelectionType() == null) {
					 * System.err.println("No selection, applying.");
					 * card.makeEffect(null, gameState);
					 * hand.remove(handClickedEvent.cardClicked); --limit; }
					 * else {
					 */
					selection = selector.getSelection(card);
					System.err.println("Received: " + selection);
					if (selection != null) {
						System.err.println("Selection received, applying.");
						card.makeEffect(selection, gameState);
						hand.remove(handClickedEvent.cardClicked);
						--limit;
					}
					gameState.update();
				}
			}
			gui.setHighlight(false);
		}
	}
}