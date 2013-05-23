package model.cards;

import static org.junit.Assert.assertEquals;
import game.controller.LocalController;
import game.controller.Selection.CellSelection;
import game.model.cards.zombies.Claws;
import game.model.cards.zombies.Zombie;

import org.junit.Test;

import controller.ForTestsOnly;

public class ClawsTest {

	@Test
	public void test() {
		CellSelection selection1 = ForTestsOnly.getNewCellSelection(1, 2);
		CellSelection selection2 = ForTestsOnly.getNewCellSelection(2, 2);
		CellSelection selection3 = ForTestsOnly.getNewCellSelection(4, 1);
		CellSelection selection4 = ForTestsOnly.getNewCellSelection(3, 2);
		LocalController controller = new LocalController();
		Claws claws = new Claws();
		new Zombie(1).makeEffect(ForTestsOnly.getNewCellSelection(4, 1),
				controller.gameState);
		new Zombie(2).makeEffect(ForTestsOnly.getNewCellSelection(3, 2),
				controller.gameState);
		assertEquals(0, claws.rateSelection(controller.gameState, selection1));
		assertEquals(0, claws.rateSelection(controller.gameState, selection2));
		assertEquals(2, claws.rateSelection(controller.gameState, selection3));
		assertEquals(2, claws.rateSelection(controller.gameState, selection4));
		int st1 = controller.gameState.getBoard().get(4, 1).getStrength();
		int st2 = controller.gameState.getBoard().get(3, 2).getStrength();
		claws.makeEffect(selection3, controller.gameState);
		claws.makeEffect(selection4, controller.gameState);
		assertEquals(st1 + 1, (int) controller.gameState.getBoard().get(4, 1)
				.getStrength());
		assertEquals(st2 + 1, (int) controller.gameState.getBoard().get(3, 2)
				.getStrength());
	}
}
