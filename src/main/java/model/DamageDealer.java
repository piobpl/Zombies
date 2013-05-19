package model;

import model.Trap.Trigger;

/**
 * Klasa wspomagająca wykonywanie działań na planszy.
 *
 * @author piob
 */
public abstract class DamageDealer {

	public static void dealDamage(GameState gameState, int x, int y, int dmg,
			Trigger type) {
		// TODO
		for (Trap t : gameState.getBoard().getTraps(x, y))
			if (t.getTriggers().contains(type)) {
				t.trigger();
				return;
			}
		if (!gameState.getBoard().isEmpty(x, y)) {
			Card c = gameState.getBoard().get(x, y);
			c.setStrength(c.getStrength() - dmg);
			if (c.getStrength() <= 0)
				c = null;
			gameState.getBoard().set(x, y, c);
		}
	}
}
