package model.cards.humans;

import model.GameState;
import model.cards.helpers.Card;
import model.cards.helpers.SolidityTester;
import model.traps.WallTrap;
import utility.Pair;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public class Wall extends Card {
	Integer height;
	
	Wall(Integer height){
		this.height=height;
	}
	
	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Pair<Integer, Integer> p=((CellSelection) selection).cell;
		if(p.first==4)
			return 0;
		for(int i=0; i<5; i++){
			for(int j=0; j<3; j++){
				if(SolidityTester.areVertexAdjacent(p, new Pair<Integer, Integer>(i, j))){
					if(gameState.getBoard().is(i, j, "Zombie") || gameState.getBoard().is(i, j, "Dogs")){
						return 0;
					}
				}
			}
		}
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Pair<Integer, Integer> p=((CellSelection) selection).cell;
		gameState.getBoard().getTraps(p.first, p.second).add(new WallTrap(gameState, height, p));
	}

	@Override
	public String getName() {
		return "Wall";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.CELL;
	}

	@Override
	public Integer getStrength() {
		return height;
	}

	@Override
	public void setStrength(Integer strength) {
		throw new java.lang.UnsupportedOperationException();
	}

}
