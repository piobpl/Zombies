package cards.zombies;

import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;
import model.Card;
import model.GameState;

public class Zombie extends Card {

	private Integer strength;
	
	public Zombie(Integer strength) {
		this.strength = strength;
	}
	
	public String getName(){
		return "Zombie";
	}
	
	public SelectionType getSelectionType(){
		return SelectionType.CELL;
	}
	
	public Integer getStrength(){
		return strength;
	}
	
	public void setStrength(Integer strength){
		this.strength = strength;
	}

	@Override
	public boolean isSelectionCorrect(GameState gameState, Selection selection) {
		Integer x = ((CellSelection)selection).cell.first;
		Integer y = ((CellSelection)selection).cell.second;
		return gameState.getBoard().isEmpty(x,y);
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection)selection).cell.first;
		Integer y = ((CellSelection)selection).cell.second;
		gameState.getBoard().set(x, y, this);
	}

}
