package model.cards.humans;

import java.util.List;

import model.GameState;
import model.cards.helpers.Card;
import model.cards.helpers.Mover;
import model.cards.helpers.SolidityTester;
import utility.Pair;
import controller.Selection;
import controller.Selection.GroupSelection;
import controller.Selection.SelectionType;

public class Blood extends Card {
	
	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		Pair<Integer, Integer> tmp;
		switch(cells.size()){
		case 2:
			tmp=cells.get(0);
			if(!(gameState.getBoard().is(tmp.first, tmp.second, "Zombie") || gameState.getBoard().is(tmp.first, tmp.second, "Dogs")))
				return 0;
			Pair<Integer, Integer> tmp2=cells.get(1);
			if(!gameState.getBoard().isEmpty(tmp2.first, tmp2.second) || !SolidityTester.areEdgeAdjacent(tmp, tmp2) || tmp2.first!=tmp.first)
				return 0;
			return 2;
		case 1:
			tmp=cells.get(0);
			if(!(gameState.getBoard().is(tmp.first, tmp.second, "Zombie") || gameState.getBoard().is(tmp.first, tmp.second, "Dogs")))
				return 0;
			return 1;
		default:
			return 0;
		}
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		Pair<Integer, Integer> tmp=cells.get(0);
		Pair<Integer, Integer> tmp2=cells.get(1);
		if(tmp.second+1==tmp2.second)
			Mover.moveRight(gameState, tmp.first, tmp.second);
		else
			Mover.moveLeft(gameState, tmp.first, tmp.second);
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
	
}
