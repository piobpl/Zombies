package model.cards.humans;

import utility.Pair;
import model.GameState;
import model.cards.helpers.Card;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public class Mine extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		if (!gameState.getBoard().isCompletelyEmpty(x, y)) {
			return 0;
		}
		if(x>0){
			if(!gameState.getBoard().isEmpty(x-1, y) && (gameState.getBoard().get(x-1, y).getName().equals("Zombie") || gameState
							.getBoard().get(x-1, y).getName().equals("Dogs"))){
				return 0;
			}
		}
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		gameState.getBoard().getTraps(x, y).add(new model.traps.MineTrap(gameState,new Pair<Integer,Integer>(x,y)));
	}

	@Override
	public String getName() {
		return "Mine";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.CELL;
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
