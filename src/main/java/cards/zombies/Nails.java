package cards.zombies;

import model.Card;
import model.GameState;
import utility.Pair;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public class Nails extends Card {

	@Override
	public boolean isSelectionCorrect(GameState gameState, Selection selection) {
		Pair<Integer, Integer> cell = ((CellSelection)selection).cell;
		if (gameState.getBoard().isEmpty(cell.first, cell.second))
			return false;
		if (!gameState.getBoard().get(cell.first, cell.second).getName().equals("Zombie"))
			return false;
		return true;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int x = ((CellSelection)selection).cell.first;
		int y = ((CellSelection)selection).cell.second;
		int currentStrength =
				gameState.getBoard().get(x, y).getStrength();
		gameState.getBoard().get(x, y).setStrength(currentStrength + 1);
		gameState.getBoard().update(x, y);
	}

	@Override
	public String getName() {
		return "Nails";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.CELL;
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
