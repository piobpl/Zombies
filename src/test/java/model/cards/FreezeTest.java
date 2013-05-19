package model.cards;

import static org.junit.Assert.assertEquals;
import model.Modifier.ModifierType;
import model.cards.humans.Freeze;

import org.junit.Test;

import controller.Controller;

public class FreezeTest {

	@Test
	public void test() {
		Controller controller = new Controller();
		Freeze freeze = new Freeze();
		freeze.makeEffect(null, controller.gameState);
		assertEquals(
				true,
				controller.gameState.getModifiers().contains(
						ModifierType.BEENFROZEN));
	}
}