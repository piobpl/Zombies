package cards.humans;

import cards.helpers.Mover;
import model.Card;
import model.GameState;
import controller.Selection;
import controller.Selection.ColumnSelection;
import controller.Selection.SelectionType;

/**
 * REFLEKTOR – Cofa (równocześnie) każdego zombiego i psa w danym torze 
 * o jedno pole, jeżeli jest to możliwe.
 * 
 * @author michal
 *
 */

public class Searchlight extends Card {

	@Override
	public boolean isSelectionCorrect(GameState gameState, Selection selection) {
		return true;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		// TODO : sprawdzanie dodatkowych przeszkod, np. muru
		int column = ((ColumnSelection)selection).column;
		for(int i = 1; i < 5; i++) Mover.moveBackward(gameState, i, column);
	}

	@Override
	public String getName() {
		return "Searchlight";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.COLUMN;
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
