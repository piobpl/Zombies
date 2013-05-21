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

	@Override
	public String getTooltipMessage() {
		return "You can shoot in a straight line (through a single lane only) and you will always hit the first target on the bullet’s path. Burst consists of two or three bullets, and each of them deals one damage point. It means, that after killing one target, the remaining bullets may strike another targets in the same lane (that is a straight line). A zombie that got hit and was not killed must take a one square step back (regardless of the number of bullets he just got hit with), if possible. Note: The card human absorbs all damage from one burst.";
	}
}
