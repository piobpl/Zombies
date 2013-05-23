package model.cards;

import static org.junit.Assert.assertEquals;
import game.controller.LocalController;
import game.model.Modifier.ModifierType;
import game.model.cards.zombies.Terror;

import org.junit.Test;


/**
 * 
 * @author krozycki
 *
 */
public class TerrorTest {

	@Test
	public void test() {
		LocalController controller = new LocalController();
		Terror terror = new Terror();
		terror.makeEffect(null, controller.gameState);
		assertEquals(
				true,
				controller.gameState.getModifiers().contains(
						ModifierType.TERROR));
	}
}
