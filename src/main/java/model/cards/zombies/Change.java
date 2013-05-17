package model.cards.zombies;

import java.util.List;

import model.Card;
import model.GameState;
import model.cards.helpers.SolidityTester;
import utility.Pair;
import controller.Selection;
import controller.Selection.GroupSelection;
import controller.Selection.SelectionType;

public class Change extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		if (cells.size() < 2) {
			return 1;
		}
		if (cells.size() > 2) {
			return 0;
		}
		for (int i = 1; i < cells.size(); i++) {
			if (!SolidityTester.areEdgeAdjacent(cells.get(i), cells.get(i - 1))) {
				return 0;
			}
		}
		for (int i = 0; i < cells.size(); i++) {
			int x = cells.get(i).first;
			int y = cells.get(i).second;
			if (gameState.getBoard().isEmpty(x, y)
					|| !gameState.getBoard().get(x, y).getName()
							.equals("Zombie")) {
				return 0;
			}
		}
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		int x1 = cells.get(0).first;
		int y1 = cells.get(0).second;
		int x2 = cells.get(1).first;
		int y2 = cells.get(1).second;
		gameState.getBoard().exchangeContent(x1, y1, x2, y2);
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

}
