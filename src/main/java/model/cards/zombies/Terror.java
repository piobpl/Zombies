package model.cards.zombies;

import model.GameState;
import model.cards.helpers.Card;
import model.modifiers.Modifier;
import model.modifiers.ModifierType;
import controller.Selection;
import controller.Selection.SelectionType;

public class Terror extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		gameState.globalModifiers.add(new Modifier(ModifierType.TERROR, 5));
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
