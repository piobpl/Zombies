package model.cards.humans;

import model.Card;
import model.GameState;
import model.SelectionTester;
import model.traps.WallTrap;
import utility.Pair;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public class Wall extends Card {
	Integer height;

	public Wall(Integer height) {
		this.height = height;
	}

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Pair<Integer, Integer> p = ((CellSelection) selection).cell;
		if (p.first == 4)
			return 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				if (SelectionTester.areVertexAdjacent(p,
						new Pair<Integer, Integer>(i, j))) {
					if (gameState.getBoard().is(i, j, CardType.ZOMBIE)
							|| gameState.getBoard().is(i, j, CardType.DOGS)) {
						return 0;
					}
				}
			}
		}
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Pair<Integer, Integer> p = ((CellSelection) selection).cell;
		gameState.getBoard().getTraps(p.first, p.second)
				.add(new WallTrap(gameState, height, p));
	}

	@Override
	public String getName() {
		return "Wall";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.CELL;
	}

	@Override
	public Integer getStrength() {
		return height;
	}

	@Override
	public void setStrength(Integer strength) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public CardType getType() {
		return CardType.WALL;
	}
}
