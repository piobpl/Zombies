package model.cards.zombies;

import java.util.Iterator;

import model.Card;
import model.GameState;
import model.Trap;
import model.Trap.TrapType;
import utility.TypedSet;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public class PickAxe extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		if (!gameState.getBoard().getTraps(x, y).isEmpty()) {
			return 2;
		}
		return 0;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		TypedSet<Trap, TrapType> traps = gameState.getBoard().getTraps(x, y);
		Iterator<Trap> it = traps.iterator();
		while (it.hasNext()) {
			Trap tmpTrap = it.next();
			if (!(tmpTrap.getType() == TrapType.PIT || tmpTrap.getType() == TrapType.BARRIER)) {
				it.remove();
			}
		}
	}

	@Override
	public String getName() {
		return "Pick Axe";
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
		return CardType.PICKAXE;
	}

	@Override
	public String getTooltipMessage() {
		return " It destroys one obstacle put on the street by the human player.";
	}

}
