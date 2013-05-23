package game.controller;

import game.model.GameState;
import game.model.Hand;
import game.model.Player;
import game.view.EventReceiver.ButtonClickedEvent;
import game.view.EventReceiver.Event;
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
		Event event;
		Hand hand = gameState.getHand(player);
		int pos;
		if (hand.isEmpty())
			return;
		for (;;) {
			gui.getInfoPanel()
					.sendMessage("Choose one card to throw away.");
			event = gui.eventReceiver.getNextEvent();
			if (event.type != EventType.HandClicked
					|| event.mouseButtonId != MouseEvent.BUTTON1)
				continue;
			pos = ((HandClickedEvent) event).cardClicked;
			if (hand.isEmpty(pos))
				continue;
			if (((HandClickedEvent) event).player == player) {
				gui.getInfoPanel().sendMessage(
						"Please confirm your choice.");
				gui.getHand(player).getCell(pos).setHighlight(true);
				gui.setButtonEnabled(Button.ApplySelection, true);
				gui.setButtonEnabled(Button.CancelSelection, true);
				boolean applied = false;
				for (;;) {
					Event confirmEvent = gui.eventReceiver.getNextEvent();
					if (confirmEvent.type != EventType.ButtonClicked
							|| confirmEvent.mouseButtonId != MouseEvent.BUTTON1) {
						continue;
					}
					if (((ButtonClickedEvent) confirmEvent).button == Button.ApplySelection) {
						gui.getHand(player).getCell(pos)
								.setHighlight(false);
						applied = true;
						break;
					}
					if (((ButtonClickedEvent) confirmEvent).button == Button.CancelSelection) {
						gui.getHand(player).getCell(pos)
								.setHighlight(false);
						break;
					}
				}
				gui.setButtonEnabled(Button.ApplySelection, false);
				gui.setButtonEnabled(Button.CancelSelection, false);
				if (applied) {
					break;
				}
			}
		}
		System.err.println("Discarded: " + hand.get(pos).getName());
		hand.set(pos, null);
		gameState.update();
	}
}