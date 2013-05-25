package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.DamageDealer;
import game.model.GameState;
import game.model.Trap.Trigger;
import game.model.traps.NapalmTrap;
import utility.Pair;

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

	@Override
	public String getTooltipMessage() {
		return "<html>It sets fire to any square on the street, and will burn until the next human phase.<br>Whatever is standing or moves into the burning square, suffers one point of damage.</html>";
	}
}
