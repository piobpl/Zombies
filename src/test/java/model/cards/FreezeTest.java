package model.cards;

import static org.junit.Assert.assertEquals;
import game.controller.LocalController;
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
		LocalController controller = new LocalController();
		Freeze freeze = new Freeze();
		freeze.makeEffect(null, controller.gameState);
		assertEquals(
				true,
				controller.gameState.getModifiers().contains(
						ModifierType.FROZEN));
	}
}