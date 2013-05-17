package model.cards.zombies;

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
	public int rateSelection(GameState gameState, Selection selection) {
		Pair<Integer, Integer> cell = ((CellSelection) selection).cell;
		if (gameState.getBoard().isEmpty(cell.first, cell.second))
			return 0;
		if (!gameState.getBoard().get(cell.first, cell.second).getName()
				.equals("Zombie"))
			return 0;
		if (!gameState.getBoard().isEmpty(cell.first + 1, cell.second))
			return 0;
		return 2;
	}

	/**
	 * this is temporary solution without fixing possibility!
	 */
	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		// TODO it can be REALLY complicated: i think we should use function
		// moveZombie() or sth like that
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		if (x == 4)
			throw new GameOver(Player.ZOMBIE);
		Card temp = gameState.getBoard().get(x, y);
		gameState.getBoard().remove(x, y);
		gameState.getBoard().set(x + 1, y, temp);
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
