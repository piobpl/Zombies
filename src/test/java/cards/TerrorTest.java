package cards;

import static org.junit.Assert.assertEquals;
import modifiers.ModifierType;

import org.junit.Test;

import cards.zombies.Terror;
import controller.Controller;

public class TerrorTest {

	@Test
	public void test() {
		Controller controller = new Controller();
		Terror terror = new Terror();
		terror.makeEffect(null, controller.gameState);
		assertEquals(true, controller.gameState.globalModifiers.contains(ModifierType.TERROR));
	}
}
