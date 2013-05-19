package model.cards.humans;

import model.Card;
import model.GameState;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

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
		gameState.getBoard().getTraps(x, y).clear();
	}

	@Override
	public CardType getType() {
		return CardType.HANDGRANADE;
	}
}
