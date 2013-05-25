package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.MoveMaker;

public class BackOff extends Card {

	private static final long serialVersionUID = -6832186110853807701L;

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
		return SelectionType.EMPTY;
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

	@Override
	public String getTooltipMessage() {
		return "It simultaneously moves all the zombies and dogs one square back on the street (if possible).";
	}

}
