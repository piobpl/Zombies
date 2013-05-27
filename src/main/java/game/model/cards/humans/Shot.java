package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.ColumnSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.DamageDealer;
import game.model.GameState;
import game.model.MoveMaker;
import game.model.Trap;
import game.model.Trap.Trigger;

/**
 *
 * @author michal
 *
 */

public class Shot extends Card {

	/**
	 *
	 */
	private static final long serialVersionUID = 6646133985919960933L;
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
		if (DamageDealer.askForUseOfClick(gameState))
			return;
		int column = ((ColumnSelection) selection).column;
		for (int i = 4; i >= 0; i--) {
			for(Trap trap : gameState.getBoard().getTraps(i, column))
				if(trap.getTriggers().contains(Trigger.SHOT)) {
					DamageDealer.dealDamage(gameState, i, column, strength, Trigger.SHOT);
					return;
				}
			if (!gameState.getBoard().isEmpty(i, column) && !gameState.getBoard().is(i,column,CardType.BARREL)) {
				DamageDealer.dealDamage(gameState, i, column, strength,
						Trigger.SHOT);
				MoveMaker.moveBackward(gameState, i, column);
				break;
			}
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
		return "<html>You can shoot in a straight line (through a single lane only) and you will always hit the first target on the bullet's path.<br>The shot deals damage equal to its power rating (1 or 2).<br>Additionally, a zombie that got hit and was not killed must take a one square step back , if possible.</html>";
	}

}
