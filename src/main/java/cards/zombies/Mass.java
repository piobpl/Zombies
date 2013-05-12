package cards.zombies;

import cards.helpers.SolidityTester;
import model.Card;
import model.GameState;
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
	public boolean isSelectionCorrect(GameState gameState, Selection selection) {
		if (((GroupSelection) selection).cells.size() != 2)
			return false;
		Pair<Integer, Integer> cell1 = ((GroupSelection) selection).cells
				.get(0);
		Pair<Integer, Integer> cell2 = ((GroupSelection) selection).cells
				.get(1);
		if (!SolidityTester.areEdgeAdjacent(cell1, cell2))
			return false;
		if(!SolidityTester.areInSameRow(cell1, cell2))
		if (gameState.getBoard().isEmpty(cell1.first, cell1.second)
				|| gameState.getBoard().isEmpty(cell2.first, cell2.second))
			return false;
		if (!gameState.getBoard().get(cell1.first, cell1.second).getName()
				.equals("Zombie")
				|| !gameState.getBoard().get(cell2.first, cell2.second)
						.getName().equals("Zombie"))
			return false;
		return true;
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
		gameState.getBoard().get(cell2.first, cell2.second)
				.setStrength(newStrength);
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

}
