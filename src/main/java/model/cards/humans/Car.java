package model.cards.humans;

import model.Card;
import model.GameState;
import utility.Pair;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public class Car extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;

		for (int i = x; i < 5; i++) {
			if (!gameState.getBoard().isCompletelyEmpty(i, y)) {
				return 0;
			}
		}
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		gameState
				.getBoard()
				.getTraps(x, y)
				.add(new model.traps.CarTrap(gameState,
						new Pair<Integer, Integer>(x, y)));
	}

	@Override
	public String getName() {
		return "Car";
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

	@Override
	public CardType getType() {
		return CardType.CAR;
	}
}
