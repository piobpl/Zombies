package cards.humans;

import cards.helpers.DamageDealer;
import cards.helpers.Mover;
import model.Card;
import model.GameState;
import model.Trap.Trigger;
import controller.Selection;
import controller.Selection.ColumnSelection;
import controller.Selection.SelectionType;

// TODO : obsluga dodatkowych efektow (czlowiek) i przeszkod (np. mur)

public class Shot extends Card {

	private Integer strength;

	public Shot(Integer strength) {
		this.strength = strength;
	}

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int column = ((ColumnSelection) selection).column;
		for (int i = 4; i >= 0; i--)
			if (!gameState.getBoard().isEmpty(i, column)) {
				DamageDealer.dealDamage(gameState, i, column, strength, Trigger.SHOT);
				Mover.moveBackward(gameState, i, column);
				break;
			}
	}

	@Override
	public String getName() {
		return "Shot";
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
