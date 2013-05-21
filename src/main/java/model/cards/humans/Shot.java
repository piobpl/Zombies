package model.cards.humans;

import model.Card;
import model.DamageDealer;
import model.GameState;
import model.MoveMaker;
import model.Trap.Trigger;
import controller.Selection;
import controller.Selection.ColumnSelection;
import controller.Selection.SelectionType;

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
				DamageDealer.dealDamage(gameState, i, column, strength,
						Trigger.SHOT);
				MoveMaker.moveBackward(gameState, i, column);
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

	@Override
	public CardType getType() {
		return CardType.SHOT;
	}

	@Override
	public String getTooltipMessage() {
		return "You can shoot in a straight line (through a single lane only) and you will always hit the first target on the bullet’s path. The shot deals damage equal to its power rating (1 or 2). Additionally, a zombie that got hit and was not killed must take a one square step back , if possible.";
	}

}
