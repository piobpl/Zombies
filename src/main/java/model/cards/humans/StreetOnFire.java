package model.cards.humans;

import model.Card;
import model.DamageDealer;
import model.GameState;
import model.Trap.Trigger;
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
					&& (gameState.getBoard().get(i, column).getType() == CardType.ZOMBIE || gameState
							.getBoard().get(i, column).getType() == CardType.DOGS)) {
				DamageDealer.dealDamage(gameState, i, column, 1, Trigger.FIRE);
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

	@Override
	public CardType getType() {
		return CardType.STREETONFIRE;
	}

	@Override
	public String getTooltipMessage() {
		return "You can play this card on one of the side lanes (not the central one). The fire deals one damage point to every zombie and dog on that lane.";
	}

}
