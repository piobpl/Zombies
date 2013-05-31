package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.Modifier;
import game.model.Modifier.ModifierType;

/**
 * 
 * @author michal
 *
 */

public class Boss extends Card {

	private static final long serialVersionUID = -2188445505960108360L;

	@Override
	public CardType getType() {
		return CardType.BOSS;
	}

	@Override
	public String getName() {
		return "Boss";
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
	public String getTooltipMessage() {
		return "<html>You can assign this card to any zombie on board thus making it the boss.<br>He can give three move orders (only one in a single turn).<br>He can order a backward move, forward move or side move.<br>Only zombies with strength 3 or less take orders, and any single zombie can take only one order per game.<br>If the boss dies, all zombies move back one square if such action is possible.</html>";
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		Card c = gameState.getBoard().get(x, y);
		c.getModifiers().add(
				new Modifier(ModifierType.BOSS, Integer.MAX_VALUE));
		
	}

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		if (gameState.getBoard().is(x, y, CardType.ZOMBIE)) {
			return 2;
		}
		return 0;
	}

	@Override
	public void setStrength(Integer strength) {
		throw new UnsupportedOperationException();
	}

}
