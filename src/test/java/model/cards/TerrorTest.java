package model.cards;

import static org.junit.Assert.assertEquals;
import model.Modifier.ModifierType;
import model.cards.zombies.Terror;

import org.junit.Test;

import controller.Controller;

public class TerrorTest {

	@Test
	public void test() {
		Controller controller = new Controller();
		Terror terror = new Terror();
		terror.makeEffect(null, controller.gameState);
		assertEquals(
				true,
				controller.gameState.getModifiers().contains(
						ModifierType.TERROR));
	}
}
