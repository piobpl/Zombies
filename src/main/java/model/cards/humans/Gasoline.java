package model.cards.humans;

import java.util.List;

import model.GameState;
import model.cards.helpers.Card;
import model.cards.helpers.SolidityTester;
import model.traps.Trap.Trigger;
import utility.Pair;
import controller.Selection;
import controller.Selection.GroupSelection;
import controller.Selection.SelectionType;

public class Gasoline extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		for (int i = 1; i < cells.size(); i++) {
			if (!SolidityTester.areEdgeAdjacent(cells.get(i), cells.get(i - 1))) {
				return 0;
			}
		}
		return 2;
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
			if (gameState.getBoard().isEmpty(x, y)) {
				--remainingStrength;
			} else {
				int zombieStrength = gameState.getBoard().get(x, y)
						.getStrength();
				if (remainingStrength >= zombieStrength) {
					model.cards.helpers.DamageDealer.dealDamage(gameState, x, y,
							zombieStrength, Trigger.FIRE);
					remainingStrength -= zombieStrength;
				} else {
					model.cards.helpers.DamageDealer.dealDamage(gameState, x, y,
							remainingStrength, Trigger.FIRE);
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