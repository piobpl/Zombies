package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.ColumnSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.traps.BarrierTrap;

/**
 * 
 * @author Edoipi
 * 
 */
public class Barrier extends Card {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1500121994004738086L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Integer c = ((ColumnSelection) selection).column;
		if (!gameState.getBoard().isCompletelyEmpty(4, c))
			return 0;
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer c = ((ColumnSelection) selection).column;
		for (int i = 0; i < 5; i++) {
			gameState.getBoard().getTraps(i, c).add(new BarrierTrap(4));
		}
	}

	@Override
	public String getName() {
		return "Barrier";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.COLUMN;
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
		return CardType.BARRIER;
	}

	@Override
	public String getTooltipMessage() {
		return "<html>It blocks all movement of any zombies and dogs in a single lane for 1 phase.<br>The barrier also prevents anyone from moving to a blocked lane (The player cannot put any zombies or dogs on that lane in his phase).<br>The barrier may be placed only on a free field in the first row.</html>";
	}
}
