package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.GroupSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.Modifier;
import game.model.SelectionTester;
import game.model.Modifier.ModifierType;

import java.util.List;

import utility.Pair;

/**
 * @author piob
 */
public class Net extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		for (Pair<Integer, Integer> p : cells) {
			Card c = gameState.getBoard().get(p.first, p.second);
			if (c == null || c.getType() != CardType.ZOMBIE)
				return 0;
		}
		if (!SelectionTester.areEdgeSolid(cells))
			return 0;
		if (SelectionTester.getStrength(gameState, cells) > 6)
			return 0;
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		for (Pair<Integer, Integer> p : ((GroupSelection) selection).cells) {
			Card c = gameState.getBoard().get(p.first, p.second);
			c.getModifiers().add(new Modifier(ModifierType.FROZEN, 5));
		}
	}

	@Override
	public String getName() {
		return "Net";
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
		return CardType.NET;
	}

	@Override
	public String getTooltipMessage() {
		// TODO Net.Tooltip
		return "";
	}
}