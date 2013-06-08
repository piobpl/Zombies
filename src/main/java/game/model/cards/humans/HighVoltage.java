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
public class HighVoltage extends Card {

	
	private static final long serialVersionUID = -6939991207360864713L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int column = ((ColumnSelection) selection).column;
		for (int i = 4; i >= 0; i--)
			DamageDealer.dealDamage(gameState, i, column, 1, Trigger.VOLTAGE);
	}

	@Override
	public String getName() {
		return "High Voltage";
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
		return CardType.HIGHVOLTAGE;
	}

	@Override
	public String getTooltipMessage() {
		return "<html>You can play this card on any of the three lanes.<br>High voltage deals one damage point to every zombie or dog on a given lane.</html>";
	}

}
