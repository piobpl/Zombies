package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.GroupSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.MoveMaker;
import game.model.SelectionTester;

import java.util.LinkedList;

import utility.Pair;
/**
 * 
 * @author Edoipi
 *
 */
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
		if(l.size()==1)
			return 1;
		if(l.size()>4 || l.get(1).equals(myDogs))
			return 0;
		if(!SelectionTester.areEdgeSolid(l))
			return 0;
		Pair<Integer, Integer> from=myDogs;
		Card card=gameState.getBoard().get(myDogs.first, myDogs.second);
		for(Pair<Integer, Integer> to : ((GroupSelection) selection).cells){
			if(!MoveMaker.isMovePossible(gameState, from, to, card) || !SelectionTester.areEdgeAdjacent(from, to)){
				return 0;
			}
			from=to;
		}
		if(l.size()<4)
			return 1;
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		LinkedList<Pair<Integer, Integer>> l = new LinkedList<>();
		l.add(myDogs);
		l.addAll(((GroupSelection) selection).cells);
		Pair<Integer, Integer> from=myDogs;
		for(Pair<Integer, Integer> to : ((GroupSelection) selection).cells){
			if(gameState.getBoard().isEmpty(from.first, from.second))
				break;
			MoveMaker.moveTo(gameState, from, to);
			from=to;
		}
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
