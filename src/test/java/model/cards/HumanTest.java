package model.cards;

import static org.junit.Assert.assertEquals;
import game.controller.LocalController;
import game.model.Modifier.ModifierType;
import game.model.cards.zombies.Human;
import game.model.cards.zombies.Zombie;

import org.junit.Test;

import controller.ForTestsOnly;

public class HumanTest {

	@Test
	public void test() {
		LocalController controller = new LocalController();
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
