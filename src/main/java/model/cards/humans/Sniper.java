package model.cards.humans;

import model.Card;
import model.DamageDealer;
import model.GameState;
import model.MoveMaker;
import model.Trap.Trigger;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public class Sniper extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		DamageDealer.dealDamage(gameState, x, y, 2, Trigger.SHOT);
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
		return "Choose any target and shoot. The target will move backwards if it stays alive.";
	}

}
