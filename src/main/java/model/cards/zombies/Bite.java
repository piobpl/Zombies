package model.cards.zombies;

import java.util.List;

import model.GameState;
import model.cards.helpers.Card;
import model.modifiers.ModifierType;
import utility.Pair;
import controller.Selection;
import controller.Selection.GroupSelection;
import controller.Selection.SelectionType;
import model.cards.helpers.SolidityTester;;

public class Bite extends Card{
	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		Pair<Integer, Integer> tmp;
		switch(cells.size()){
		case 2:
			tmp=cells.get(0);
			if(!gameState.getBoard().is(tmp.first, tmp.second, "Zombie") || !gameState.getBoard().get(tmp.first, tmp.second).modifiers.contains(ModifierType.HUMAN))
				return 0;
			Pair<Integer, Integer> tmp2=cells.get(1);
			if(!gameState.getBoard().isEmpty(tmp2.first, tmp2.second) || !SolidityTester.areEdgeAdjacent(tmp, tmp2) || tmp2.second<tmp.second)
				return 0;
			return 2;
		case 1:
			tmp=cells.get(0);
			if(!gameState.getBoard().is(tmp.first, tmp.second, "Zombie") || !gameState.getBoard().get(tmp.first, tmp.second).modifiers.contains(ModifierType.HUMAN))
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
		gameState.getBoard().get(tmp.first, tmp.second).modifiers.remove(ModifierType.HUMAN);
		tmp=cells.get(1);
		gameState.getBoard().set(tmp.first, tmp.second, new Zombie(1));
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

}
