package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.MoveMaker;
import utility.Pair;

public class Hunger extends Card {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7177818428693360268L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Pair<Integer, Integer> cell = ((CellSelection) selection).cell;
		if (gameState.getBoard().isEmpty(cell.first, cell.second))
			return 0;
		if (gameState.getBoard().get(cell.first, cell.second).getType() != CardType.ZOMBIE)
			return 0;
		if (!MoveMaker.isMovePossible(gameState, cell,
				new Pair<Integer, Integer>(cell.first + 1, cell.second), null)) {
			System.err.println("syf i malaria");
			return 0;
		}
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		MoveMaker.moveForward(gameState, x, y);
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

	@Override
	public CardType getType() {
		return CardType.HUNGER;
	}

	@Override
	public String getTooltipMessage() {
		return "<html>It makes one zombie move one square forward, if the forward square is not occupied or blocked.</html>";
	}

}
