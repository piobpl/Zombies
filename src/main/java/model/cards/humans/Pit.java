package model.cards.humans;

import model.Card;
import model.GameState;
import utility.Pair;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;
/**
 * 
 * @author Edoipi
 *
 */
public class Pit extends Card {
	private Integer strength;

	public Pit(Integer strength) {
		this.strength = strength;
	}

	public String getName() {
		return "Pit";
	}

	public SelectionType getSelectionType() {
		return SelectionType.CELL;
	}

	public Integer getStrength() {
		return strength;
	}

	public void setStrength(Integer strength) {
		this.strength = strength;
	}

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		if (gameState.getBoard().isEmpty(x, y))
			return 2;
		return 0;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		gameState
				.getBoard()
				.getTraps(x, y)
				.add(new model.traps.PitTrap(gameState, strength,
						new Pair<Integer, Integer>(x, y)));
	}

	@Override
	public CardType getType() {
		return CardType.PIT;
	}

	@Override
	public String getTooltipMessage() {
		return "You can play a pit trap on any square on the board. A pit trap has a depth indicator (1 or 2). If any zombie or dog whose strength is equal or less than a pit trap’s depth falls into it, it instantly dies. The pit trap is then considered “filled” (we remove it from the board and put aside).A zombie whose strength is greater than the pit’s depth will walk through it without any problem (the pit trap stays on board). A zombie hiding behind the human will always use him to fill the hole in, and walk through not affected.";
	}

}
