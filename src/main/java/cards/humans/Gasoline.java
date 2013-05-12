package cards.humans;

import java.util.List;

import model.Card;
import model.GameState;
import utility.Pair;
import cards.helpers.SolidityTester;
import controller.Selection;
import controller.Selection.GroupSelection;
import controller.Selection.SelectionType;

public class Gasoline extends Card {

	@Override
	public boolean isSelectionCorrect(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		for (int i = 1; i < cells.size(); i++) {
			if (!SolidityTester.areEdgeAdjacent(cells.get(i), cells.get(i - 1))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		int remainingStrength = 4;
		for (Pair<Integer, Integer> cell : cells) {
			if (remainingStrength == 0)
				return;
			Integer x = cell.first;
			Integer y = cell.second;
			if (gameState.getBoard().get(x, y).getName().isEmpty()) {
				--remainingStrength;
			} else {
				int zombieStrength = gameState.getBoard().get(x, y)
						.getStrength();
				if (remainingStrength >= zombieStrength) {
					cards.helpers.DamageDealer.dealDamage(gameState, x, y,
							zombieStrength);
					remainingStrength -= zombieStrength;
				} else {
					cards.helpers.DamageDealer.dealDamage(gameState, x, y,
							remainingStrength);
					remainingStrength = 0;
				}
			}
		}
	}

	@Override
	public String getName() {
		return "Gasoline";
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
