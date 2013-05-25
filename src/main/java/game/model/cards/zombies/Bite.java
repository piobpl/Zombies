package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.GroupSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.MoveMaker;
import game.model.SelectionTester;
import game.model.Trap;
import game.model.Modifier.ModifierType;

import java.util.List;

import utility.Pair;

public class Bite extends Card {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3398347028843618011L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		Pair<Integer, Integer> tmp;
		switch (cells.size()) {
		case 2:
			tmp = cells.get(0);
			if (!gameState.getBoard()
					.is(tmp.first, tmp.second, CardType.ZOMBIE)
					|| !gameState.getBoard().get(tmp.first, tmp.second)
							.getModifiers().contains(ModifierType.HUMAN))
				return 0;
			Pair<Integer, Integer> tmp2 = cells.get(1);
			if (!SelectionTester.areEdgeAdjacent(tmp, tmp2)
					|| tmp2.first > tmp.first
					|| !MoveMaker.isMovePossible(gameState, tmp, tmp2,
							new Zombie(1)))
				return 0;
			return 2;
		case 1:
			// System.err.println("1");
			tmp = cells.get(0);
			if (!gameState.getBoard()
					.is(tmp.first, tmp.second, CardType.ZOMBIE)
					|| !gameState.getBoard().get(tmp.first, tmp.second)
							.getModifiers().contains(ModifierType.HUMAN))
				return 0;
			return 1;
		default:
			return 0;
		}
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		Pair<Integer, Integer> tmp = cells.get(0);
		gameState.getBoard().get(tmp.first, tmp.second).getModifiers()
				.remove(ModifierType.HUMAN);
		tmp = cells.get(1);
		Card card = new Zombie(1);
		gameState.getBoard().set(tmp.first, tmp.second, card);
		for (Trap t : gameState.getBoard().getTraps(tmp.first, tmp.second)) {
			t.movedOn(card);
		}
	}

	@Override
	public String getName() {
		return "Bite";
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
		return CardType.BITE;
	}

	@Override
	public String getTooltipMessage() {
		return "<html>You can play this card if there is a zombie with the human shield on board.<br>A bitten human changes into a zombie with strength/toughness of 1 and is put directly beside or behind his creator.</html>";
	}
}
