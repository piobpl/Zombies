package cards.humans;

import cards.helpers.DamageDealer;
import cards.helpers.Mover;
import model.Card;
import model.GameState;
import controller.Selection;
import controller.Selection.ColumnSelection;
import controller.Selection.SelectionType;

// TODO : obsluga dodatkowych efektow (czlowiek) i przeszkod (np. mur)

public class Shot extends Card {

	@Override
	public boolean isSelectionCorrect(GameState gameState, Selection selection) {
		return true;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int column = ((ColumnSelection) selection).column;
		for (int i = 4; i >= 0; i--)
			if (!gameState.getBoard().isEmpty(i, column)) {
				DamageDealer.dealDamage(gameState, i, column, 1);
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
		return null;
	}

	@Override
	public void setStrength(Integer strength) {
		throw new java.lang.UnsupportedOperationException();
	}

}
