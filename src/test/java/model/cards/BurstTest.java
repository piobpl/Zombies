package model.cards;

import static org.junit.Assert.assertEquals;
import game.controller.LocalController;
import game.controller.Selection.ColumnSelection;
import game.model.cards.humans.Burst;
import game.model.cards.zombies.Zombie;

import org.junit.Test;

import controller.ForTestsOnly;

public class BurstTest {

	@Test
	public void test() {
		ColumnSelection selection = ForTestsOnly.getNewColumnSelection(1);
		LocalController controller = new LocalController();
		Burst burst = new Burst(4);
		new Zombie(2).makeEffect(ForTestsOnly.getNewCellSelection(3, 1),
				controller.gameState);
		new Zombie(3).makeEffect(ForTestsOnly.getNewCellSelection(2, 1),
				controller.gameState);
		assertEquals(2, burst.rateSelection(controller.gameState, selection));
		burst.makeEffect(selection, controller.gameState);
		assertEquals(true, controller.gameState.getBoard().isEmpty(3, 1));
		assertEquals(true, controller.gameState.getBoard().isEmpty(2, 1));
		assertEquals(false, controller.gameState.getBoard().isEmpty(1, 1));
		assertEquals(new Integer(1), controller.gameState.getBoard().get(1, 1)
				.getStrength());
	}
}
