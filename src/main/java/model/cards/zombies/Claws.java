package model.cards.zombies;

import model.GameState;
import model.cards.helpers.Card;
import utility.Pair;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public class Claws extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Pair<Integer, Integer> cell = ((CellSelection) selection).cell;
		if (gameState.getBoard().isEmpty(cell.first, cell.second))
			return 0;
		if (!gameState.getBoard().get(cell.first, cell.second).getName()
				.equals("Zombie"))
			return 0;
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		int currentStrength = gameState.getBoard().get(x, y).getStrength();
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
