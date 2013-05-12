package cards.zombies;

import model.Card;
import model.GameState;
import model.Player;
import utility.Pair;
import controller.GameOver;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public class Hunger extends Card {

	@Override
	public boolean isSelectionCorrect(GameState gameState, Selection selection) {
		Pair<Integer, Integer> cell = ((CellSelection)selection).cell;
		if (gameState.getBoard().isEmpty(cell.first, cell.second))
			return false;
		if (!gameState.getBoard().get(cell.first, cell.second).getName().equals("Zombie"))
			return false;
		//TODO it's gonna be more complicated
		if (cell.first.equals(4)) {
			//jakbym zapomnial dodac issue - proponuje zrobic board rozmiaru [6][4]
			//zeby indeksowanie zgadzalo sie z intuicja
			throw new GameOver(Player.ZOMBIE);
		}
		if (!gameState.getBoard().isEmpty(cell.first + 1, cell.second))
			return false;
		return true;
	}
	
	/**
	 * this is temporary solution without fixing possibility!
	 */
	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		// TODO it can be REALLY complicated: i think we should use function
		// moveZombie() or sth like that
		int x = ((CellSelection)selection).cell.first;
		int y = ((CellSelection)selection).cell.second;
		Card temp =
				gameState.getBoard().get(x, y);
		gameState.getBoard().remove(x, y);
		gameState.getBoard().set(x+1, y, temp);
	}

	@Override
	public String getName() {
		return "Hunger";
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
