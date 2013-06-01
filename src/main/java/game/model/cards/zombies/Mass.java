package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.GroupSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.Modifier;
import game.model.Modifier.ModifierType;
import game.model.MoveMaker;
import game.model.SelectionTester;

import java.util.List;

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

	/**
	 *
	 */
	private static final long serialVersionUID = 6442372778772153609L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		if (cells.size() == 1) {
			Pair<Integer, Integer> cell = cells.get(0);
			if (gameState.getBoard().is(cell.first, cell.second,
					CardType.ZOMBIE))
				return 1;
			return 0;
		}
		if (cells.size() != 2)
			return 0;
		Pair<Integer, Integer> cell1 = cells.get(0);
		Pair<Integer, Integer> cell2 = cells.get(1);
		if (!SelectionTester.areEdgeAdjacent(cell1, cell2))
			return 0;
		if (!gameState.getBoard()
				.is(cell1.first, cell1.second, CardType.ZOMBIE))
			return 0;
		if (!gameState.getBoard()
				.is(cell2.first, cell2.second, CardType.ZOMBIE))
			return 0;
		Card temp = gameState.getBoard().get(cell2.first, cell2.second);
		gameState.getBoard().remove(cell2.first, cell2.second);
		if (!MoveMaker.isMovePossible(gameState, cell1, cell2, null)) {
			gameState.getBoard().set(cell2.first, cell2.second, temp);
			return 0;
		}
		gameState.getBoard().set(cell2.first, cell2.second, temp);
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
		boolean human = (gameState.getBoard().get(cell1.first, cell1.second)
				.getModifiers().contains(ModifierType.HUMAN) ||
				gameState.getBoard().get(cell2.first, cell2.second)
				.getModifiers().contains(ModifierType.HUMAN));
			
		System.err.println("Nowy zombiak z sila: " + newStrength);
		gameState.getBoard().get(cell2.first, cell2.second)
				.setStrength(newStrength);
		gameState.getBoard().get(cell2.first, cell2.second).getModifiers()
				.add(new Modifier(ModifierType.MOVEDONCE, 8));
		if (human)
			gameState.getBoard().get(cell2.first, cell2.second).getModifiers()
					.add(new Modifier(ModifierType.HUMAN, Integer.MAX_VALUE));
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
