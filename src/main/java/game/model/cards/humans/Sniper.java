package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.DamageDealer;
import game.model.DamageDealer.DamageEffect;
import game.model.GameState;
import game.model.MoveMaker;
import game.model.Trap.Trigger;

public class Sniper extends Card {

	/**
	 *
	 * @author michal
	 */
	private static final long serialVersionUID = 8765665340909045835L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		if (DamageDealer.askForUseOfClick(gameState))
			return;
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		if (DamageDealer.dealDamage(gameState, x, y, 2, Trigger.SHOT) ==
				DamageEffect.DAMAGED)
			MoveMaker.moveBackward(gameState, x, y);
	}

	@Override
	public String getName() {
		return "Sniper";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.CELL;
	}

	@Override
	public Integer getStrength() {
		return 2;
	}

	@Override
	public void setStrength(Integer strength) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public CardType getType() {
		return CardType.SNIPER;
	}

	@Override
	public String getTooltipMessage() {
		return "<html>Choose any target and shoot.<br>The target will move backwards if it stays alive.</html>";
	}

}
