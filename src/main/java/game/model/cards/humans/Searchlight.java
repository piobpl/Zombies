package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.ColumnSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.MoveMaker;

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
		int column = ((ColumnSelection) selection).column;
		for (int i = 1; i < 5; i++)
			MoveMaker.moveBackward(gameState, i, column);
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

	@Override
	public CardType getType() {
		return CardType.SEARCHLIGHT;
	}

	@Override
	public String getTooltipMessage() {
		return "It simultaneously moves all zombies and dogs one square back on a single lane (if possible).";
	}

}
