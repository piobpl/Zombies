package model.cards.humans;

import model.Card;
import model.GameState;
import model.MoveMaker;
import controller.Selection;
import controller.Selection.SelectionType;

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
				MoveMaker.moveBackward(gameState, i, j);
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

	@Override
	public CardType getType() {
		return CardType.BACKOFF;
	}

}
