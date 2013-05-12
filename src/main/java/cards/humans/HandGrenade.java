package cards.humans;

import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;
import model.Card;
import model.GameState;

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

	}

	@Override
	public boolean isSelectionCorrect(GameState gameState, Selection selection) {
		return true;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		gameState.getBoard().remove(x, y);
	}

}
