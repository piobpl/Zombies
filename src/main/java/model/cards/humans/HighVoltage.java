package model.cards.humans;

import model.Card;
import model.GameState;
import model.Trap.Trigger;
import model.cards.helpers.DamageDealer;
import controller.Selection;
import controller.Selection.ColumnSelection;
import controller.Selection.SelectionType;

public class HighVoltage extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int column = ((ColumnSelection) selection).column;
		for (int i = 4; i >= 0; i--) {
			if (!gameState.getBoard().isEmpty(i, column)
					&& (gameState.getBoard().get(i, column).getName().equals("Zombie") || gameState
							.getBoard().get(i, column).getName().equals("Dogs"))) {
				DamageDealer.dealDamage(gameState, i, column, 1, Trigger.VOLTAGE);
			}
		}
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

}
