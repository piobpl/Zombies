package game.controller;

import game.model.GameState;
import game.model.Hand;
import game.model.Player;
import game.view.EventReceiver.ButtonClickedEvent;
import game.view.EventReceiver.ClickEvent;
import game.view.EventReceiver.EventType;
import game.view.EventReceiver.HandClickedEvent;
import game.view.GUI;
import game.view.GUI.Button;

import java.awt.event.MouseEvent;

public class DiscardingStage implements Stage {
	public final GameState gameState;
	public final GUI gui;

	public DiscardingStage(GameState gameState, GUI gui) {
		this.gameState = gameState;
		this.gui = gui;
	}

	public void perform(Player player) {
		ClickEvent event;
		Hand hand = gameState.getHand(player);
		int pos = -1;
		if (hand.isEmpty())
			return;
		gameState.sendMessage("Choose one card to throw away.");
		for (;;) {
			gui.setButtonEnabled(Button.ApplySelection, pos != -1);
			gui.setButtonEnabled(Button.CancelSelection, pos != -1);
			event = gui.getEventReceiver().getNextClickEvent();
			if (event.info.getButton() != MouseEvent.BUTTON1)
				continue;
			if (event.type == EventType.ButtonClicked && pos!=-1) {
				if (((ButtonClickedEvent) event).button == Button.ApplySelection)
					break;
				if (((ButtonClickedEvent) event).button == Button.CancelSelection) {
						gui.getHand(player).getCell(pos).setHighlight(false);
						pos=-1;
				}
			} else if (event.type == EventType.HandClicked) {
				if (((HandClickedEvent) event).player != player)
					continue;
				if (pos != -1)
					gui.getHand(player).getCell(pos).setHighlight(false);
				pos = ((HandClickedEvent) event).cardClicked;
				if (hand.isEmpty(pos))
					pos = -1;
				if (pos != -1 && event.info.getClickCount() > 1)
					break;
				if (pos != -1)
					gui.getHand(player).getCell(pos).setHighlight(true);
			}
		}
		System.err.println("Discarded: " + hand.get(pos).getName());
		gui.setButtonEnabled(Button.ApplySelection, false);
		gui.setButtonEnabled(Button.CancelSelection, false);
		gui.getHand(player).getCell(pos).setHighlight(false);
		hand.set(pos, null);
		gameState.update();
	}

	@Override
	public StageType getStageType() {
		return StageType.DISCARDING;
	}
}