package model.cards;

import static org.junit.Assert.assertEquals;
import game.controller.Controller;
import game.model.Modifier.ModifierType;
import game.model.cards.humans.Freeze;

import org.junit.Test;


/**
 * 
 * @author krozycki
 *
 */
public class FreezeTest {

	@Test
	public void test() {
		Controller controller = new Controller();
		Freeze freeze = new Freeze();
		freeze.makeEffect(null, controller.gameState);
		assertEquals(
				true,
				controller.gameState.getModifiers().contains(
						ModifierType.FROZEN));
	}
}