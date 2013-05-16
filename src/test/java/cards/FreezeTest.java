package cards;

import static org.junit.Assert.assertEquals;
import modifiers.ModifierType;

import org.junit.Test;

import cards.humans.Freeze;
import controller.Controller;

public class FreezeTest {

	@Test
	public void test() {
		Controller controller = new Controller();
		Freeze freeze = new Freeze();
		freeze.makeEffect(null, controller.gameState);
		assertEquals(true, controller.gameState.globalModifiers.contains(ModifierType.BEENFROZEN));
	}
}