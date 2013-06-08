package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.ColumnSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.DamageDealer;
import game.model.GameState;
import game.model.Trap.Trigger;

/**
 * 
 * @author krozycki
 * 
 */
public class StreetOnFire extends Card {

	
	private static final long serialVersionUID = 6397362608070665296L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		int column = ((ColumnSelection) selection).column;
		if (!(column == 0 || column == 2)) {
			return 0;
		}
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int column = ((ColumnSelection) selection).column;
		for (int i = 4; i >= 0; i--)
			DamageDealer.dealDamage(gameState, i, column, 1, Trigger.FIRE);
	}

	@Override
	public String getName() {
		return "Street on fire";
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
		return CardType.STREETONFIRE;
	}

	@Override
	public String getTooltipMessage() {
		return "<html>You can play this card on one of the side lanes (not the central one).<br>The fire deals one damage point to every zombie and dog on that lane.</html>";
	}

}
