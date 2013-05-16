package cards.zombies;

import java.util.List;

import model.Card;
import model.GameState;
import modifiers.ModifierType;
import utility.Pair;
import controller.Selection;
import controller.Selection.GroupSelection;
import controller.Selection.SelectionType;

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
			tmp=cells.get(1);
			if(!gameState.getBoard().isEmpty(tmp.first, tmp.second))
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
