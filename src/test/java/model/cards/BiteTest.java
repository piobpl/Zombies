package model.cards;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import model.cards.zombies.Bite;
import model.cards.zombies.Human;
import model.cards.zombies.Zombie;

import org.junit.Test;

import utility.Pair;
import controller.Controller;
import controller.ForTestsOnly;

public class BiteTest {

	@Test
	public void test() {
		Controller controller = new Controller();
		new Zombie(2).makeEffect(ForTestsOnly.getNewCellSelection(3, 1),
				controller.gameState);
		List<Pair<Integer, Integer>> cells=new LinkedList<>();
		cells.add(new Pair<Integer, Integer>(1, 1));
		Bite b=new Bite();
		Human h=new Human();
		assertEquals(0, b.rateSelection(controller.gameState, ForTestsOnly.getNewGroupSelection(cells)));
		cells.clear();
		cells.add(new Pair<Integer, Integer>(3, 1));
		assertEquals(0, b.rateSelection(controller.gameState, ForTestsOnly.getNewGroupSelection(cells)));
		h.makeEffect(ForTestsOnly.getNewCellSelection(3, 1), controller.gameState);
		assertEquals(1, b.rateSelection(controller.gameState, ForTestsOnly.getNewGroupSelection(cells)));
		cells.add(new Pair<Integer, Integer>(2, 1));
		assertEquals(0, b.rateSelection(controller.gameState, ForTestsOnly.getNewGroupSelection(cells)));
		cells.remove(1);
		assertEquals(1, b.rateSelection(controller.gameState, ForTestsOnly.getNewGroupSelection(cells)));
		cells.add(new Pair<Integer, Integer>(3, 0));
		assertEquals(2, b.rateSelection(controller.gameState, ForTestsOnly.getNewGroupSelection(cells)));
		b.makeEffect(ForTestsOnly.getNewGroupSelection(cells), controller.gameState);
		assertEquals(true, controller.gameState.getBoard().is(3, 0, "Zombie"));
	}
}
