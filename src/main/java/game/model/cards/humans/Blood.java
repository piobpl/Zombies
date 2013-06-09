package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.GroupSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.MoveMaker;
import game.model.SelectionTester;

import java.util.List;

import utility.Pair;
/**
 * 
 * @author Edoipi
 *
 */
public class Blood extends Card {

	
	private static final long serialVersionUID = -4987020517835308841L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		Pair<Integer, Integer> tmp;
		switch (cells.size()) {
		case 2:
			tmp = cells.get(0);
			if (!(gameState.getBoard().is(tmp.first, tmp.second,
					CardType.ZOMBIE) || gameState.getBoard().is(tmp.first,
					tmp.second, CardType.DOGS)))
				return 0;
			Pair<Integer, Integer> tmp2 = cells.get(1);
			if (!gameState.getBoard().isEmpty(tmp2.first, tmp2.second)
					|| !SelectionTester.areEdgeAdjacent(tmp, tmp2)
					|| tmp2.first != tmp.first)
				return 0;
			return 2;
		case 1:
			tmp = cells.get(0);
			if (!(gameState.getBoard().is(tmp.first, tmp.second,
					CardType.ZOMBIE) || gameState.getBoard().is(tmp.first,
					tmp.second, CardType.DOGS)))
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
		Pair<Integer, Integer> tmp2 = cells.get(1);
		if (tmp.second + 1 == tmp2.second)
			MoveMaker.moveRight(gameState, tmp.first, tmp.second);
		else
			MoveMaker.moveLeft(gameState, tmp.first, tmp.second);
	}

	@Override
	public String getName() {
		return "Blood";
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
		return CardType.BLOOD;
	}

	@Override
	public String getTooltipMessage() {
		return "<html>It forces one zombie or a dog to move sideways.</html>";
	}

}
