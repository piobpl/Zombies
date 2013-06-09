package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.ColumnSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.DamageDealer;
import game.model.GameState;
import game.model.MoveMaker;
import game.model.Trap.Trigger;

/**
 *
 * @author michal, jerzozwierz
 *
 */

public class Burst extends Card {

	
	private static final long serialVersionUID = -6308527386433743150L;
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
		if (DamageDealer.askForUseOfClick(gameState))
			return;
		int column = ((ColumnSelection) selection).column;
		int remaining = strength;
		for (int i = 4; i >= 0; i--) {
			switch (DamageDealer.dealDamage(gameState, i, column, 1, Trigger.SHOT)) {
			
			case ABSORBED:
				remaining = 0;
				break;
			case SHOT:
			case KILLED:
				--remaining;
				break;
			case DAMAGED:
				--remaining;
				if (remaining == 0)
					MoveMaker.moveBackward(gameState, i, column);
				++i;
			case NOTHING:
			}
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
		return "<html>You can shoot in a straight line (through a single lane only) and you will always hit the first target on the bullet's path.<br>Burst consists of two or three bullets, and each of them deals one damage point.<br>It means, that after killing one target, the remaining bullets may strike another targets in the same lane (that is a straight line).<br>A zombie that got hit and was not killed must take a one square step back (regardless of the number of bullets he just got hit with), if possible.<br>Note: The card human absorbs all damage from one burst.</html>";
	}
}
