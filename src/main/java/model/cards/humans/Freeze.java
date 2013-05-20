package model.cards.humans;

import model.Card;
import model.GameState;
import model.Modifier;
import model.Modifier.ModifierType;
import controller.Selection;
import controller.Selection.SelectionType;

public class Freeze extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		gameState.getModifiers().add(new Modifier(ModifierType.BEENFROZEN, 5));
	}

	@Override
	public String getName() {
		return "Freeze";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.EMPTY;
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
		return CardType.FREEZE;
	}

}
