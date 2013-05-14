package cards.zombies;

import controller.Selection;
import controller.Selection.SelectionType;
import model.Card;
import model.GameState;

public class Terror extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		//TODO ustawic globalmodifier terror na 1
	}

	@Override
	public String getName() {
		return "Terror";
	}

	@Override
	public SelectionType getSelectionType() {
		return null;
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
