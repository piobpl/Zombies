package cards.humans;

import controller.Selection;
import controller.Selection.CellSelection;
import model.Card;
import model.GameState;

public class HandGrenade extends Card {

	public final String name = "Hand grenade";
	
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
		gameState.getBoard().remove(x, y);
	}

}
