package model.cards;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import model.cards.humans.HandGrenade;
import model.cards.humans.Mine;
import model.cards.zombies.Zombie;
import model.traps.Trap;

import org.junit.Test;

import controller.Controller;
import controller.ForTestsOnly;

public class HandGrenadeTest {
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
		HandGrenade handGrenade = new HandGrenade();
		handGrenade.makeEffect(ForTestsOnly.getNewCellSelection(3, 2),
				controller.gameState);
		a = 0;
		it = controller.gameState.getBoard().getTraps(3, 2).iterator();
		while (it.hasNext()) {
			if (it.next().getName().equals("Mine")) {
				a++;
			}
		}
		assertEquals(0, a);
		handGrenade.makeEffect(ForTestsOnly.getNewCellSelection(0, 0),
				controller.gameState);
		assertEquals(null, controller.gameState.getBoard().get(0, 0));
		for (int i = 0; i <= 4; i++) {
			for (int j = 0; j <= 2; j++) {
				assertEquals(true, controller.gameState.getBoard()
						.isCompletelyEmpty(i, j));
			}
		}
	}
}
