package model.cards.humans;

import model.GameState;
import model.cards.helpers.Card;
import model.cards.helpers.DamageDealer;
import model.cards.helpers.Mover;
import model.traps.Trap.Trigger;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

// TODO : obsluga dodatkowych efektow (czlowiek) i przeszkod (np. mur)

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
		Mover.moveBackward(gameState, x, y);

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

}