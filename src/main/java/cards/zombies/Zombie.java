package cards.zombies;

import controller.Selection;
import controller.Selection.CellSelection;
import model.Card;
import model.GameState;

public class Zombie extends Card {

	public final String name = "Zombie";
	public final Integer strength;
	
	public Zombie(Integer strength) {
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
