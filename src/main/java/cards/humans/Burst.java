package cards.humans;

import cards.helpers.DamageDealer;
import cards.helpers.Mover;
import model.Card;
import model.GameState;
import model.Trap.Trigger;
import controller.Selection;
import controller.Selection.ColumnSelection;
import controller.Selection.SelectionType;

// TODO : karta "czlowiek"!

public class Burst extends Card {

	private Integer strength;

	public Burst(Integer strength) {
		this.strength = strength;
	}

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int column = ((ColumnSelection) selection).column;
		int remaining = strength;
		for (int i = 4; i >= 0; i--)
			if (!gameState.getBoard().isEmpty(i, column)) {
				int damage = Math.min(remaining,
						gameState.getBoard().get(i, column).getStrength());
				remaining -= damage;
				DamageDealer.dealDamage(gameState, i, column, damage, Trigger.SHOT);
				Mover.moveBackward(gameState, i, column);
				if (remaining == 0)
					break;
			}
	}

	@Override
	public String getName() {
		return "Burst";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.COLUMN;
	}

	@Override
	public Integer getStrength() {
		return strength;
	}

	@Override
	public void setStrength(Integer strength) {
		throw new java.lang.UnsupportedOperationException();
	}

}
