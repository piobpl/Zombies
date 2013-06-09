package model.cards;

import static org.junit.Assert.assertEquals;
import game.controller.LocalController;
import game.model.Trap;
import game.model.Trap.TrapType;
import game.model.cards.humans.Mine;
import game.model.cards.zombies.Zombie;

import org.junit.Test;

import controller.ForTestsOnly;

/**
 *
 * @author krozycki
 *
 */
public class MineTest {

	@Test
	public void test() {
		LocalController controller = new LocalController();
		Mine mine = new Mine();
		assertEquals(
				2,
				mine.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(3, 2)));
		mine.makeEffect(ForTestsOnly.getNewCellSelection(3, 2),
				controller.gameState);
		int a = 0;
		for (Trap t : controller.gameState.getBoard().getTraps(3, 2).asList())
			if (t.getType() == TrapType.MINE) {
				a++;
			}
		assertEquals(1, a);
		assertEquals(
				0,
				mine.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(3, 2)));
		new Zombie(1).makeEffect(ForTestsOnly.getNewCellSelection(0, 0),
				controller.gameState);
		Mine secondmine = new Mine();
		assertEquals(
				0,
				secondmine.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(0, 0)));
		assertEquals(
				0,
				secondmine.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(1, 0)));
		assertEquals(
				2,
				secondmine.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(2, 0)));
	}
}
