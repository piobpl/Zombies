package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.GroupSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.DamageDealer;
import game.model.GameState;
import game.model.SelectionTester;
import game.model.Trap.Trigger;

import java.util.List;

import utility.Pair;

/**
 * 
 * @author jerzozwierz
 *
 */
public class Gasoline extends Card {

	private static final long serialVersionUID = 7404283742318109948L;

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
		for (int i=0; i<cells.size(); i++) {
			if (remainingStrength == 0)
				return;
			Integer x = cells.get(i).first;
			Integer y = cells.get(i).second;
			switch (DamageDealer.dealDamage(gameState, x, y, 1, Trigger.FIRE)) {
			
			case ABSORBED:
				return;
			case DAMAGED:
				--i;
			case KILLED:
			case NOTHING:
			case SHOT:
				--remainingStrength;
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
		return "<html>You can spill burning gasoline on any horizontally or vertically adjacent squares.<br>Gasoline has a power of 4, which means it can deal a total of 4 damage points to zombies and/or dogs remaining in burning squares.<br>To ignite another square in a row, the gasoline must kill a zombie on the previous square.<br>You can also spill gasoline on an empty square, but, in such a case, one damage point is wasted.</html>";
	}
}
