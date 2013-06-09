package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;

/**
 * @author piob
 */

public class Click extends Card {

	/**
	 *
	 */
	private static final long serialVersionUID = -6308527386433743150L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 0;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getName() {
		return "Click";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.UNPLAYABLE;
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
		return CardType.CLICK;
	}

	@Override
	public String getTooltipMessage() {
		return "This card can be played in human phase as response to shot, burst or sniper.<br>It counters these cards.";
	}
}
