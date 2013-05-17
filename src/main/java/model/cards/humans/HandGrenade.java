package model.cards.humans;

import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;
import model.GameState;
import model.cards.helpers.Card;

// TODO : usuwanie muru etc.
public class HandGrenade extends Card {

	public String getName() {
		return "Hand grenade";
	}

	public SelectionType getSelectionType() {
		return SelectionType.CELL;
	}

	public Integer getStrength() {
		return null;
	}

	public void setStrength(Integer strength) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		gameState.getBoard().remove(x, y);
	}

}
