package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.GroupSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.SelectionTester;

import java.util.LinkedList;

import utility.Pair;

public class DogsMover extends Card {
	Pair<Integer, Integer> myDogs;
	public DogsMover(Pair<Integer, Integer> p){
		myDogs=p;
	}
	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		LinkedList<Pair<Integer, Integer>> l = new LinkedList<>();
		l.add(myDogs);
		l.addAll(((GroupSelection) selection).cells);
		if(!SelectionTester.areEdgeSolid(l))
			return 0;
		return 1;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		gameState.getBoard().set(x, y, this);
	}

	@Override
	public String getName() {
		return "DogsMover";
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
		return CardType.DOGS;
	}

	@Override
	public String getTooltipMessage() {
		return "";
	}

}
