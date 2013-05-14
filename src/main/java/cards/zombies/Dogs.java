package cards.zombies;

import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;
import model.Card;
import model.GameState;

public class Dogs extends Card {

	private Integer strength = 1;

	@Override
	public boolean isSelectionCorrect(GameState gameState, Selection selection) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		return gameState.getBoard().isEmpty(x, y) && x.equals(0);
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		gameState.getBoard().set(x, y, this);
	}

	@Override
	public String getName() {
		return "Dogs";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.CELL;
	}

	@Override
	public Integer getStrength() {
		return strength;
	}

	@Override
	public void setStrength(Integer strength) {
		this.strength = strength;
	}

}
