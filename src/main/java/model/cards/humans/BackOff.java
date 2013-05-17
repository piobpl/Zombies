package model.cards.humans;

import controller.Selection;
import controller.Selection.SelectionType;
import model.GameState;
import model.cards.helpers.Card;
import model.cards.helpers.Mover;

public class BackOff extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				if (gameState.getBoard().isEmpty(i, j))
					continue;
				Mover.moveBackward(gameState, i, j);
			}
		}
	}

	@Override
	public String getName() {
		return "Back off";
	}

	@Override
	public SelectionType getSelectionType() {
		return null; // deal with it, or make new SelectionType.NOTHING or sth
						// like that
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
