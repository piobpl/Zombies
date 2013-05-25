package game.model;

import game.model.Card.CardType;
import game.model.Trap.Trigger;

/**
 * Klasa wspomagająca wykonywanie działań na planszy.
 *
 * @author piob
 */

public abstract class DamageDealer {

	public static void dealDamage(GameState gameState, int x, int y, int dmg,
			Trigger type) {
		for (Trap t : gameState.getBoard().getTraps(x, y))
			if (t.getTriggers().contains(type)) {
				t.trigger();
				if (type == Trigger.SHOT)
					return;
			}
		if (gameState.getBoard().get(x, y) != null
				&& gameState.getBoard().get(x, y).getType() != CardType.BARREL) {
			Card c = gameState.getBoard().get(x, y);
			c.setStrength(c.getStrength() - dmg);
			if (c.getStrength() <= 0)
				c = null;
			gameState.getBoard().set(x, y, c);
		}
	}
}
