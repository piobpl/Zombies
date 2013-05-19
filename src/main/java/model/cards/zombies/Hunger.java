package model.cards.zombies;

import model.Card;
import model.GameState;
import model.MoveMaker;
import utility.Pair;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public class Hunger extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Pair<Integer, Integer> cell = ((CellSelection) selection).cell;
		if (gameState.getBoard().isEmpty(cell.first, cell.second))
			return 0;
		if (gameState.getBoard().get(cell.first, cell.second).getType() != CardType.ZOMBIE)
			return 0;
		if (!gameState.getBoard().isEmpty(cell.first + 1, cell.second))
			return 0;
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		MoveMaker.moveForward(gameState, x, y);
	}

	@Override
	public String getName() {
		return "Hunger";
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
		return CardType.HUNGER;
	}

}
