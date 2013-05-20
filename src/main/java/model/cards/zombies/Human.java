package model.cards.zombies;

import model.Card;
import model.GameState;
import model.Modifier;
import model.Modifier.ModifierType;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;
/**
 * 
 * @author Edoipi
 *
 */
public class Human extends Card {

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
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		Card c = gameState.getBoard().get(x, y);
		c.getModifiers().add(
				new Modifier(ModifierType.HUMAN, Integer.MAX_VALUE));
	}

	@Override
	public String getName() {
		return "Human";
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
		return CardType.HUMAN;
	}

}
