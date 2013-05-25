package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.Modifier;
import game.model.Modifier.ModifierType;

/**
 * 
 * @author krozycki
 *
 */
public class Terror extends Card {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6829482289223574892L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		gameState.getModifiers().add(new Modifier(ModifierType.TERROR, 5));
	}

	@Override
	public String getName() {
		return "Terror";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.EMPTY;
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
		return CardType.TERROR;
	}

	@Override
	public String getTooltipMessage() {
		return "In the next human phase, the human player can play only one card.";
	}
}
