package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.GroupSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.Modifier;
import game.model.MoveMaker;
import game.model.SelectionTester;
import game.model.Modifier.ModifierType;
import utility.Pair;

/**
 * Card, which is not put into the board. Requires two-element group of cells as
 * selection: cells must be adjacent and placed in the same row. In effect,
 * zombie from first cell is merged to zombie from second cell. Eventually,
 * bigger zombie remains on second cell, first cell is cleared.
 *
 * @author jerzozwierz
 *
 */

public class Mass extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		if (((GroupSelection) selection).cells.size() == 1)
			return 1;
		if (((GroupSelection) selection).cells.size() != 2)
			return 0;
		Pair<Integer, Integer> cell1 = ((GroupSelection) selection).cells
				.get(0);
		Pair<Integer, Integer> cell2 = ((GroupSelection) selection).cells
				.get(1);
		if (!SelectionTester.areEdgeAdjacent(cell1, cell2))
			return 0;
		if(!MoveMaker.isMovePossible(gameState, cell1, cell2, null))
			return 0;
		if (!gameState.getBoard()
				.is(cell1.first, cell1.second, CardType.ZOMBIE))
			return 0;
		if (!gameState.getBoard()
				.is(cell2.first, cell2.second, CardType.ZOMBIE))
			return 0;
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Pair<Integer, Integer> cell1 = ((GroupSelection) selection).cells
				.get(0);
		Pair<Integer, Integer> cell2 = ((GroupSelection) selection).cells
				.get(1);
		int newStrength = gameState.getBoard().get(cell2.first, cell2.second)
				.getStrength()
				+ gameState.getBoard().get(cell1.first, cell1.second)
						.getStrength();
		System.err.println("Nowy zombiak z sila: " + newStrength);
		gameState.getBoard().get(cell2.first, cell2.second)
				.setStrength(newStrength);
		gameState.getBoard().get(cell2.first, cell2.second).getModifiers()
				.add(new Modifier(ModifierType.MOVEDONCE, 8));
		gameState.getBoard().set(cell1.first, cell1.second, null);
	}

	@Override
	public String getName() {
		return "Mass";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.GROUP;
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
		return CardType.MASS;
	}

	@Override
	public String getTooltipMessage() {
		return "<html>A chosen zombie must move to an adjacent square (forward, backward or sideways) already occupied by another zombie.<br>In effect, we get a brand new zombie with strength equal to the sum of strength stats of the two zombies of which it is composed.<br>The new zombie cannot move until the next zombie phase!</html>";
	}

}
