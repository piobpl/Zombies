package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.Trap;
import game.model.Trap.TrapType;

import java.util.List;

import utility.TypedSet;

/**
 *
 * @author krozycki
 *
 */
public class PickAxe extends Card {

	/**
	 *
	 */
	private static final long serialVersionUID = -2191600406340397278L;

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
		List<Trap> list = traps.asList();
		for (Trap t : list)
			if (!(t.getType() == TrapType.PIT || t.getType() == TrapType.BARRIER)) {
				traps.remove(t);
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
		return "<html>It destroys one obstacle put on the street by the human player.</html>";
	}

}
