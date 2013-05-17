package model.cards.zombies;

import model.GameState;
import model.cards.helpers.Card;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;


//TODO narazie usuwa wszystkie trapy z pola, no ale chyba i tak moze byc tylko jedno

public class PickAxe extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		if(!gameState.getBoard().getTraps(x, y).isEmpty()){
			return 2;
		}
		return 0;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		gameState.getBoard().getTraps(x, y).clear();
	}

	@Override
	public String getName() {
		return "Pick Axe";
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
