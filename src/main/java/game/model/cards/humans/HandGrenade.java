package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;

public class HandGrenade extends Card {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8319798427023734570L;

	public String getName() {
		return "Hand grenade";
	}

	public SelectionType getSelectionType() {
		return SelectionType.CELL;
	}

	public Integer getStrength() {
		return null;
	}

	public void setStrength(Integer strength) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		gameState.getBoard().remove(x, y);
		gameState.getBoard().getTraps(x, y).clear();
	}

	@Override
	public CardType getType() {
		return CardType.HANDGRANADE;
	}

	@Override
	public String getTooltipMessage() {
		return "You can throw it on any square on board. Grenade kills and destroys anything that lays on that square.";
	}
}
