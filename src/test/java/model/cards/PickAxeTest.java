package model.cards;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import model.cards.humans.Mine;
import model.cards.zombies.PickAxe;
import model.cards.zombies.Zombie;
import model.traps.Trap;

import org.junit.Test;

import controller.Controller;
import controller.ForTestsOnly;

public class PickAxeTest {
	@Test
	public void test() {
		Controller controller = new Controller();
		Mine mine = new Mine();
		assertEquals(
				2,
				mine.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(3, 2)));
		mine.makeEffect(ForTestsOnly.getNewCellSelection(3, 2),
				controller.gameState);
		int a = 0;
		Iterator<Trap> it = controller.gameState.getBoard().getTraps(3, 2)
				.iterator();
		while (it.hasNext()) {
			if (it.next().getName().equals("Mine")) {
				a++;
			}
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
		PickAxe pickAxe = new PickAxe();
		assertEquals(
				0,
				pickAxe.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(0, 0)));
		assertEquals(
				2,
				pickAxe.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(3, 2)));
		pickAxe.makeEffect(ForTestsOnly.getNewCellSelection(3, 2),
				controller.gameState);
		a = 0;
		it = controller.gameState.getBoard().getTraps(3, 2)
				.iterator();
		while (it.hasNext()) {
			if (it.next().getName().equals("Mine")) {
				a++;
			}
		}
		assertEquals(0, a);
	}
}
