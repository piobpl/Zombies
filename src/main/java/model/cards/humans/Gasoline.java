package model.cards.humans;

import java.util.List;

import model.Card;
import model.GameState;
import model.SelectionTester;
import model.Trap.Trigger;
import utility.Pair;
import controller.Selection;
import controller.Selection.GroupSelection;
import controller.Selection.SelectionType;

public class Gasoline extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		for (int i = 1; i < cells.size(); i++) {
			if (!SelectionTester
					.areEdgeAdjacent(cells.get(i), cells.get(i - 1))) {
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
					model.DamageDealer.dealDamage(gameState, x, y,
							zombieStrength, Trigger.FIRE);
					remainingStrength -= zombieStrength;
				} else {
					model.DamageDealer.dealDamage(gameState, x, y,
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

	@Override
	public CardType getType() {
		return CardType.GASOLINE;
	}

	@Override
	public String getTooltipMessage() {
		return "You can spill burning gasoline on any horizontally or vertically adjacent squares. Gasoline has a power of 4, which means it can deal a total of 4 damage points to zombies and/or dogs remaining in burning squares. To ignite another square in a row, the gasoline must kill a zombie on the previous square. You can also spill gasoline on an empty square, but, in such a case, one damage point is wasted.";
	}
}
