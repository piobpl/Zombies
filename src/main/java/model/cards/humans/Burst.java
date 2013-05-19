package model.cards.humans;

import model.Card;
import model.DamageDealer;
import model.GameState;
import model.Modifier.ModifierType;
import model.MoveMaker;
import model.Trap.Trigger;
import controller.Selection;
import controller.Selection.ColumnSelection;
import controller.Selection.SelectionType;

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
		Card card;
		for (int i = 4; i >= 0; i--)
			if (!gameState.getBoard().isEmpty(i, column)) {
				card = gameState.getBoard().get(i, column);
				if (card == null)
					continue;
				if (card.getModifiers().contains(ModifierType.HUMAN)) {
					card.getModifiers().remove(ModifierType.HUMAN);
					return;
				}
				int damage = Math.min(remaining,
						gameState.getBoard().get(i, column).getStrength());
				remaining -= damage;
				DamageDealer.dealDamage(gameState, i, column, damage,
						Trigger.SHOT);
				MoveMaker.moveBackward(gameState, i, column);
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

	@Override
	public CardType getType() {
		return CardType.BURST;
	}
}
