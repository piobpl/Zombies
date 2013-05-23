package model.cards;

import static org.junit.Assert.assertEquals;
import game.controller.LocalController;
import game.controller.Selection.ColumnSelection;
import game.model.cards.humans.Shot;
import game.model.cards.zombies.Zombie;

import org.junit.Test;

import controller.ForTestsOnly;

public class ShotTest {

	@Test
	public void test() {
		ColumnSelection selection1 = ForTestsOnly.getNewColumnSelection(1);
		ColumnSelection selection2 = ForTestsOnly.getNewColumnSelection(2);
		LocalController controller = new LocalController();
		Shot shot = new Shot(1);
		new Zombie(1).makeEffect(ForTestsOnly.getNewCellSelection(4, 1),
				controller.gameState);
		new Zombie(2).makeEffect(ForTestsOnly.getNewCellSelection(3, 2),
				controller.gameState);
		assertEquals(2, shot.rateSelection(controller.gameState, selection1));
		shot.makeEffect(selection1, controller.gameState);
		shot.makeEffect(selection2, controller.gameState);
		assertEquals(true, controller.gameState.getBoard().isEmpty(4, 1));
		assertEquals(true, controller.gameState.getBoard().isEmpty(3, 1));
		assertEquals(true, controller.gameState.getBoard().isEmpty(3, 2));
		assertEquals(false, controller.gameState.getBoard().isEmpty(2, 2));
		assertEquals(new Integer(1), controller.gameState.getBoard().get(2, 2)
				.getStrength());
	}
}
