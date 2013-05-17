package cards;

import static org.junit.Assert.assertEquals;
import model.cards.humans.Freeze;
import model.modifiers.ModifierType;

import org.junit.Test;

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