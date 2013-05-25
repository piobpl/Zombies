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

	@Override
	public String getTooltipMessage() {
		return "<html>A living shield! Humans can be played on any zombie card on the street. A human moves with a given zombie.<br>One zombie can hide behind only one human. The living shield absorbs all damage from a single source, after which the card is discarded. The only exception to this rule is the grenade card, which destroys the zombie along with his human shield.</html>";
	}

}
