package game.model;

import game.model.Card.CardType;
import game.model.Modifier.ModifierType;
import game.model.Trap.Trigger;
import game.view.EventReceiver;
import game.view.EventReceiver.ButtonClickedEvent;
import game.view.EventReceiver.Event;
import game.view.EventReceiver.EventType;
import game.view.GUI;
import game.view.GUI.Button;

/**
 * Klasa wspomagająca wykonywanie działań na planszy.
 *
 * @author piob
 */

public abstract class DamageDealer {
	/**
	 * 
	 * @param gameState
	 * @param x
	 * @param y
	 * @param dmg
	 * @param type
	 * @return true iff someone has been directly damaged, but not killed
	 */
	public static boolean dealDamage(GameState gameState, int x, int y, int dmg,
			Trigger type) {
		for (Trap t : gameState.getBoard().getTraps(x, y))
			if (t.getTriggers().contains(type)) {
				t.trigger();
				if (type == Trigger.SHOT)
					return false;
			}
		if (gameState.getBoard().get(x, y) != null
				&& gameState.getBoard().get(x, y).getType() != CardType.BARREL) {
			if (dmg > 0 && gameState.getBoard().get(x, y).getType() == CardType.ZOMBIE
					&& gameState.getBoard().get(x, y).getModifiers()
							.contains(ModifierType.HUMAN)) {
				gameState.getBoard().get(x, y).getModifiers().remove(ModifierType.HUMAN);
				return false;
			}
			Card c = gameState.getBoard().get(x, y);
			c.setStrength(c.getStrength() - dmg);
			if (c.getStrength() <= 0) {
				gameState.getBoard().remove(x, y);
				return false;
			}
			gameState.getBoard().set(x, y, c);
			return true;
		}
		return false;
	}

	public static boolean askForUseOfClick(GameState gameState) {
		int pos = -1;
		for (int i = 0; i < 4; ++i) {
			Card c = gameState.getHand(Player.ZOMBIE).get(i);
			if (c != null && c.getType() == CardType.CLICK)
				pos = i;
		}
		if (pos == -1)
			return false;
		Player actualPlayer=gameState.getPlayer();
		gameState.setPlayer(Player.ZOMBIE);
		gameState.sendMessage("Do you want to use click?");
		gameState.setPlayer(actualPlayer);
		GUI gui = gameState.gui;
		gui.setButtonEnabled(Button.ApplySelection, true);
		gui.setButtonEnabled(Button.CancelSelection, true);
		EventReceiver events = gameState.gui.getEventReceiver();
		try {
			while (true) {
				Event e = events.getNextClickEvent();
				if (e.type == EventType.ButtonClicked) {
					if (((ButtonClickedEvent) e).button == Button.ApplySelection) {
						gameState.getHand(Player.ZOMBIE).set(pos, null);
						return true;
					}
					if (((ButtonClickedEvent) e).button == Button.CancelSelection)
						return false;
				}
			}
		} finally {
			gui.setButtonEnabled(Button.ApplySelection, false);
			gui.setButtonEnabled(Button.CancelSelection, false);
		}
	}
}
