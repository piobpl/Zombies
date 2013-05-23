package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Board;
import game.model.Card;
import game.model.GameState;
import game.model.Trap.TrapType;
import utility.Pair;

/**
 * @author piob
 */
public class Barrel extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Pair<Integer, Integer> cell = ((CellSelection) selection).cell;
		if (cell.first != 4)
			return 0;
		Board board = gameState.getBoard();
		if(!board.isEmpty(cell.first, cell.second))
			return 0;
		if(board.getTraps(cell.first, cell.second).containsAny(TrapType.BARRIER, TrapType.WALL))
			return 0;
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Pair<Integer, Integer> cell = ((CellSelection) selection).cell;
		gameState.getBoard().set(cell.first, cell.second, this);
	}

	@Override
	public String getName() {
		return "Barrel";
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
		return CardType.BARREL;
	}

	@Override
	public String getTooltipMessage() {
		return "You can put the barrel on one of the three fifth row squares, of course providing that a given square is not blocked by a zombie, dog, wall or barrier. The barrel moves one square forward (towards the graveyard) at the beginning of every humans’ phase. When the rolling barrel meets a zombie or dog, it kills them instantly and is destroyed itself (removed from play). The  barrel crashes into a wall or a car, and can fall into a pit trap.";
	}
}
