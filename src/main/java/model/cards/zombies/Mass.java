package model.cards.zombies;

import model.Card;
import model.GameState;
import model.Modifier;
import model.Modifier.ModifierType;
import model.SelectionTester;
import utility.Pair;
import controller.Selection;
import controller.Selection.GroupSelection;
import controller.Selection.SelectionType;

/**
 * Card, which is not put into the board. Requires two-element group of cells as
 * selection: cells must be adjacent and placed in the same row. In effect,
 * zombie from first cell is merged to zombie from second cell. Eventually,
 * bigger zombie remains on second cell, first cell is cleared.
 *
 * @author jerzozwierz
 *
 */

public class Mass extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		if (((GroupSelection) selection).cells.size() == 1)
			return 1;
		if (((GroupSelection) selection).cells.size() != 2)
			return 0;
		Pair<Integer, Integer> cell1 = ((GroupSelection) selection).cells
				.get(0);
		Pair<Integer, Integer> cell2 = ((GroupSelection) selection).cells
				.get(1);
		if (!SelectionTester.areEdgeAdjacent(cell1, cell2))
			return 0;
		if (!gameState.getBoard()
				.is(cell1.first, cell1.second, CardType.ZOMBIE))
			return 0;
		if (!gameState.getBoard()
				.is(cell2.first, cell2.second, CardType.ZOMBIE))
			return 0;
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Pair<Integer, Integer> cell1 = ((GroupSelection) selection).cells
				.get(0);
		Pair<Integer, Integer> cell2 = ((GroupSelection) selection).cells
				.get(1);
		int newStrength = gameState.getBoard().get(cell2.first, cell2.second)
				.getStrength()
				+ gameState.getBoard().get(cell1.first, cell1.second)
						.getStrength();
		System.err.println("Nowy zombiak z sila: " + newStrength);
		gameState.getBoard().get(cell2.first, cell2.second)
				.setStrength(newStrength);
		gameState.getBoard().get(cell2.first, cell2.second).getModifiers()
				.add(new Modifier(ModifierType.MOVEDONCE, 8));
		gameState.getBoard().update(cell2.first, cell2.second);
		gameState.getBoard().set(cell1.first, cell1.second, null);
	}

	@Override
	public String getName() {
		return "Mass";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.GROUP;
	}

	@Override
	public Integer getStrength() {
		return null;
	}

	@Override
	public void setStrength(Integer strength) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public CardType getType() {
		return CardType.MASS;
	}

}
