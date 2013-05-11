package cards.zombies;

import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;
import model.Card;
import model.GameState;

public class Zombie extends Card {

	private Integer strength;
	//TODO remember to clean it after round!
	@SuppressWarnings("unused")
	private boolean cantMove = false;
	public void banMoving() {
		cantMove = true;
	}
	
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
		//TODO warning: isEmpty doesn't mean cell is empty
		//need to be corrected in appropriate time
			}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection)selection).cell.first;
		Integer y = ((CellSelection)selection).cell.second;
		gameState.getBoard().set(x, y, this);
	}

}
