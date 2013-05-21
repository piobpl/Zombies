package model.cards.humans;

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
		if (SelectionTester.getZombiesStrength(gameState, cells) > 6)
			return 0;
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		for (Pair<Integer, Integer> p : ((GroupSelection) selection).cells) {
			Card c = gameState.getBoard().get(p.first, p.second);
			c.getModifiers().add(new Modifier(ModifierType.BEENFROZEN, 5));
			gameState.getBoard().update(p.first, p.second);
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
