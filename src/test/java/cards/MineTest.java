package cards;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import model.Trap;

import org.junit.Test;

import cards.humans.Mine;
import cards.zombies.Zombie;
import controller.Controller;
import controller.ForTestsOnly;

public class MineTest {

	@Test
	public void test() {
		Controller controller = new Controller();
		Mine mine = new Mine();
		assertEquals(2,mine.rateSelection(controller.gameState,ForTestsOnly.getNewCellSelection(3, 2)));
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
		assertEquals(0,mine.rateSelection(controller.gameState,ForTestsOnly.getNewCellSelection(3, 2)));
		new Zombie(1).makeEffect(ForTestsOnly.getNewCellSelection(0, 0),
				controller.gameState);
		Mine secondmine = new Mine();
		assertEquals(0,secondmine.rateSelection(controller.gameState,ForTestsOnly.getNewCellSelection(0, 0)));
		assertEquals(0,secondmine.rateSelection(controller.gameState,ForTestsOnly.getNewCellSelection(1, 0)));
		assertEquals(2,secondmine.rateSelection(controller.gameState,ForTestsOnly.getNewCellSelection(2, 0)));
	}
}