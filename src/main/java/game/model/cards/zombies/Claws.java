package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import utility.Pair;

public class Claws extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Pair<Integer, Integer> cell = ((CellSelection) selection).cell;
		if (gameState.getBoard().isEmpty(cell.first, cell.second))
			return 0;
		if (gameState.getBoard().get(cell.first, cell.second).getType() != CardType.ZOMBIE)
			return 0;
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		int currentStrength = gameState.getBoard().get(x, y).getStrength();
		gameState.getBoard().get(x, y).setStrength(currentStrength + 1);
	}

	@Override
	public String getName() {
		return "Nails";
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
		return CardType.CLAWS;
	}

	@Override
	public String getTooltipMessage() {
		return "You can play claws on any zombie on board. His strength/toughness goes up by 1 point.";
	}

}
