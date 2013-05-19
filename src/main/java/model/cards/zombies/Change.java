package model.cards.zombies;

import java.util.List;

import model.Card;
import model.GameState;
import model.Modifier;
import model.Modifier.ModifierType;
import model.SelectionTester;
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
			if (!SelectionTester
					.areEdgeAdjacent(cells.get(i), cells.get(i - 1))) {
				return 0;
			}
		}
		for (int i = 0; i < cells.size(); i++) {
			int x = cells.get(i).first;
			int y = cells.get(i).second;
			if (gameState.getBoard().isEmpty(x, y)
					|| gameState.getBoard().get(x, y).getType() != CardType.ZOMBIE
					|| gameState.getBoard().get(x, y).getModifiers()
							.contains(ModifierType.MOVEDONCE)) {
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
		gameState.getBoard().get(x1, y1).getModifiers()
				.add(new Modifier(ModifierType.MOVEDONCE, 8));
		gameState.getBoard().get(x2, y2).getModifiers()
				.add(new Modifier(ModifierType.MOVEDONCE, 8));
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
}
