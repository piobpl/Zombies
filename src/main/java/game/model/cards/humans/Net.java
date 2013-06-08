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

	
	private static final long serialVersionUID = -4606365008486993227L;

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
		return "<html>You can throw a net on any number of zombies standing on vertically or horizontally adjacent squares, providing that their combined strength does not exceed 6.<br>The net prevents them from moving in their next phase.<br>(they cannot make their obligatory move, and they also cannot be moved by means of any  cards played by the zombie player, or by boss orders)<br>The net stops working at the beginning of the next human's phase.</html>";
	}
}
