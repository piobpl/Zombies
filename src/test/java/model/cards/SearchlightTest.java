package model.cards;

import static org.junit.Assert.assertEquals;
import game.controller.Controller;
import game.controller.Selection.ColumnSelection;
import game.model.cards.humans.Searchlight;
import game.model.cards.zombies.Zombie;

import org.junit.Test;

import controller.ForTestsOnly;

public class SearchlightTest {

	@Test
	public void test() {
		ColumnSelection selection = ForTestsOnly.getNewColumnSelection(1);
		Controller controller = new Controller();
		Searchlight searchlight = new Searchlight();
		new Zombie(2).makeEffect(ForTestsOnly.getNewCellSelection(4, 1),
				controller.gameState);
		new Zombie(3).makeEffect(ForTestsOnly.getNewCellSelection(3, 1),
				controller.gameState);
		new Zombie(3).makeEffect(ForTestsOnly.getNewCellSelection(0, 1),
				controller.gameState);
		assertEquals(2,
				searchlight.rateSelection(controller.gameState, selection));
		searchlight.makeEffect(selection, controller.gameState);
		assertEquals(true, controller.gameState.getBoard().isEmpty(4, 1));
		assertEquals(false, controller.gameState.getBoard().isEmpty(3, 1));
		assertEquals(false, controller.gameState.getBoard().isEmpty(2, 1));
		assertEquals(true, controller.gameState.getBoard().isEmpty(1, 1));
		assertEquals(false, controller.gameState.getBoard().isEmpty(0, 1));
	}
}
