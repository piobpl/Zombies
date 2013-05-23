package model.cards;

import static org.junit.Assert.assertEquals;
import game.controller.LocalController;
import game.model.cards.humans.Car;
import game.model.cards.zombies.Zombie;

import org.junit.Test;

import controller.ForTestsOnly;

public class CarTest {

	@Test
	public void test() {
		LocalController controller = new LocalController();
		new Zombie(2).makeEffect(ForTestsOnly.getNewCellSelection(3, 1),
				controller.gameState);
		Car c = new Car();
		assertEquals(
				0,
				c.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(3, 1)));
		assertEquals(
				2,
				c.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(3, 0)));
		assertEquals(
				2,
				c.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(4, 1)));
		assertEquals(
				0,
				c.rateSelection(controller.gameState,
						ForTestsOnly.getNewCellSelection(2, 1)));
		c.makeEffect(ForTestsOnly.getNewCellSelection(3, 0),
				controller.gameState);
		// assertEquals(true, controller.gameState.getBoard().getTraps(3,
		// 0).contains(CarTrap));
		System.err.println(controller.gameState.getBoard().getTraps(3, 0));
	}
}
