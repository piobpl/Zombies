package model.cards.humans;

import model.Card;
import model.DamageDealer;
import model.GameState;
import model.Trap.Trigger;
import model.traps.NapalmTrap;
import utility.Pair;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

/**
 * @author piob
 */
public class Napalm extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Pair<Integer, Integer> cell = ((CellSelection) selection).cell;
		DamageDealer.dealDamage(gameState, cell.first, cell.second, 1,
				Trigger.HOLLOW);
		gameState.getBoard().getTraps(cell.first, cell.second)
				.add(new NapalmTrap(gameState, cell));
	}

	@Override
	public String getName() {
		return "Napalm";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.CELL;
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
		return CardType.NAPALM;
	}
}
