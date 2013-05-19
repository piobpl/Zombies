package model.cards;

import static org.junit.Assert.assertEquals;
import model.Modifier.ModifierType;
import model.cards.zombies.Human;
import model.cards.zombies.Zombie;

import org.junit.Test;

import controller.Controller;
import controller.ForTestsOnly;

public class HumanTest {

	@Test
	public void test() {
		Controller controller = new Controller();
		new Zombie(2).makeEffect(ForTestsOnly.getNewCellSelection(3, 1),
				controller.gameState);
		Human h = new Human();
		assertEquals(
				0,
				h.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(1, 1)));
		assertEquals(
				2,
				h.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(3, 1)));
		h.makeEffect(ForTestsOnly.getNewCellSelection(3, 1),
				controller.gameState);
		assertEquals(true, controller.gameState.getBoard().get(3, 1)
				.getModifiers().contains(ModifierType.HUMAN));
	}
}
