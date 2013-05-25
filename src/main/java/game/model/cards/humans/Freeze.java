package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.Modifier;
import game.model.Modifier.ModifierType;

/**
 * 
 * @author krozycki
 *
 */
public class Freeze extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		gameState.getModifiers().add(new Modifier(ModifierType.FROZEN, 5));
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

	@Override
	public String getTooltipMessage() {
		return "<html>It freezes all movement on the street.<br>Zombies and dogs don't move in their next phase.<br>Freeze ends with the next human's phase.</html>";
	}

}
