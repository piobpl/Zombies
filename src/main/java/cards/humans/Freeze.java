package cards.humans;

import model.Card;
import model.GameState;
import modifiers.BeenFrozenModifier;
import controller.Selection;
import controller.Selection.SelectionType;

public class Freeze extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		gameState.globalModifiers.add(new BeenFrozenModifier(5));
	}

	@Override
	public String getName() {
		return "Freeze";
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
