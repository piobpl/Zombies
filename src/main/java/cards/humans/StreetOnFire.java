package cards.humans;

import cards.helpers.DamageDealer;
import cards.zombies.Dogs;
import cards.zombies.Zombie;
import controller.Selection;
import controller.Selection.ColumnSelection;
import controller.Selection.SelectionType;
import model.Card;
import model.GameState;

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
			if (!gameState.getBoard().isEmpty(i, column) && (gameState.getBoard().get(i, column).getClass()==Zombie.class || gameState.getBoard().get(i, column).getClass()==Dogs.class)) {
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
