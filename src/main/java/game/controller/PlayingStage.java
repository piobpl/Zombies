package game.controller;

import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.Hand;
import game.model.Modifier.ModifierType;
import game.model.Player;
import game.view.EventReceiver.ButtonClickedEvent;
import game.view.EventReceiver.ClickEvent;
import game.view.EventReceiver.EventType;
import game.view.EventReceiver.HandClickedEvent;
import game.view.GUI;
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
		ClickEvent event;
		HandClickedEvent handClickedEvent;
		Hand hand = gameState.getHand(player);
		Card card;
		Selection selection;
		int limit = 4;
		boolean endWarning = false;
		if (player == Player.HUMAN
				&& gameState.getModifiers().contains(ModifierType.TERROR))
			limit = 1;
		gameState.sendMessage("Choose cards to play or end turn.");
		gui.setButtonEnabled(Button.EndTurn, true);
		while (true) {
			if ((limit == 0 || gameState.getHand(player).isEmpty())
					&& !endWarning) {
				gameState.sendMessage("You have to end turn now.");
				endWarning = true;
			}
			event = gui.getEventReceiver().getNextClickEvent();
			if (event.info.getButton() != MouseEvent.BUTTON1)
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
					if (card.getSelectionType() == SelectionType.UNPLAYABLE)
						continue;
					gui.getHand(player).getCell(handClickedEvent.cardClicked)
							.setHighlight(true);
					selection = selector.getSelection(card);
					System.err.println("Received: " + selection);
					if (selection != null) {
						System.err.println("Selection received, applying.");
						gui.setButtonEnabled(Button.EndTurn, false);
						card.makeEffect(selection, gameState);
						gui.setButtonEnabled(Button.EndTurn, true);
						hand.remove(handClickedEvent.cardClicked);
						--limit;
					}
					gameState.update();
				}
			}
			gui.setHighlight(false);
		}
		gui.setButtonEnabled(Button.EndTurn, false);
	}

	@Override
	public StageType getStageType() {
		return StageType.PLAYING;
	}
}