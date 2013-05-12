package cards.helpers;

import model.Card;
import model.GameState;

/**
 * Klasa wspomagająca wykonywanie działań na planszy.
 * 
 * @author piob
 */
public abstract class DamageDealer {

	public static void dealDamage(GameState gameState, int x, int y, int dmg) {
		if (!gameState.getBoard().isEmpty(x, y)) {
			Card c = gameState.getBoard().get(x, y);
			c.setStrength(c.getStrength() - dmg);
			if (c.getStrength() <= 0)
				c = null;
			gameState.getBoard().set(x, y, c);
		}
	}

}
