package cards.zombies;

import model.Card;
import model.GameState;
import controller.Selection;
import controller.Selection.SelectionType;

public class PickAxe extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Pick Axe";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.CELL; // SelectionType.Modifier?
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
