package model.cards.zombies;

import java.util.HashSet;
import java.util.Iterator;

import model.GameState;
import model.cards.helpers.Card;
import model.traps.Trap;
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
		HashSet<Trap> traps = gameState.getBoard().getTraps(x, y);
		Iterator<Trap> it = traps.iterator();
		while (it.hasNext()) {
			Trap tmpTrap = it.next();
			if (!(tmpTrap.getName().equals("Pit") || tmpTrap.getName().equals(
					"Barrier"))) {
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

}
