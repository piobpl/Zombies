package model.cards.humans;

import model.Card;
import model.GameState;
import model.cards.helpers.Mover;
import controller.Selection;
import controller.Selection.ColumnSelection;
import controller.Selection.SelectionType;

/**
 * REFLEKTOR – Cofa (równocześnie) każdego zombiego i psa w danym torze o jedno
 * pole, jeżeli jest to możliwe.
 *
 * @author michal
 *
 */

public class Searchlight extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		// TODO : sprawdzanie dodatkowych przeszkod, np. muru
		int column = ((ColumnSelection) selection).column;
		for (int i = 1; i < 5; i++)
			Mover.moveBackward(gameState, i, column);
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
