package cards.humans;

import model.Card;
import model.GameState;
import cards.helpers.DamageDealer;
import controller.Selection;
import controller.Selection.ColumnSelection;
import controller.Selection.SelectionType;

public class StreetOnFire extends Card {

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
		for (int i = 4; i >= 0; i--) {
			if (!gameState.getBoard().isEmpty(i, column)
					&& (gameState.getBoard().get(i, column).getName() == "Zombie" || gameState
							.getBoard().get(i, column).getName() == "Dogs")) {
				DamageDealer.dealDamage(gameState, i, column, 1);
			}
		}
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

}
