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

import java.util.List;

import utility.Pair;

/**
 * 
 * @author krozycki, zajac
 *
 */
public class Change extends Card {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9087585680864326786L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		if (cells.size() == 0)
			return 1;
		if (cells.size() == 1) {
			Pair<Integer, Integer> crds = cells.get(0);
			if (gameState.getBoard().is(crds.first, crds.second, CardType.ZOMBIE))
				return 1;
			return 0;
		}
		if (cells.size() > 2) {
			return 0;
		}
		Pair<Integer, Integer> cell1 = cells.get(0);
		Pair<Integer, Integer> cell2 = cells.get(1);
		if(!SelectionTester.areEdgeAdjacent(cell1, cell2))
			return 0;
		if(gameState.getBoard().is(cell1.first, cell1.second, CardType.ZOMBIE))
			return 0;
		if(gameState.getBoard().is(cell2.first, cell2.second, CardType.ZOMBIE))
			return 0;
		if(MoveMaker.isMergePossible(gameState, cell1, cell2, null) &&
				MoveMaker.isMergePossible(gameState, cell2, cell1, null))
			return 2;
		return 0;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		int x1 = cells.get(0).first;
		int y1 = cells.get(0).second;
		int x2 = cells.get(1).first;
		int y2 = cells.get(1).second;
		if (gameState.getBoard().exchangeContent(x1, y1, x2, y2)) {
			gameState.getBoard().get(x1, y1).getModifiers()
					.add(new Modifier(ModifierType.MOVEDONCE, 8));
			gameState.getBoard().get(x2, y2).getModifiers()
					.add(new Modifier(ModifierType.MOVEDONCE, 8));
		}
	}

	@Override
	public String getName() {
		return "Change";
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
		return CardType.CHANGE;
	}

	@Override
	public String getTooltipMessage() {
		return "<html>Two zombies on horizontally or vertically adjacent squares can change places with each other.</html>";
	}
}
