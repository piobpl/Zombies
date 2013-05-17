package model.cards.humans;

import model.GameState;
import model.cards.helpers.Card;
import utility.Pair;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public class Pit extends Card{
	private Integer strength;

	public Pit(Integer strength) {
		this.strength = strength;
	}

	public String getName() {
		return "Pit";
	}

	public SelectionType getSelectionType() {
		return SelectionType.CELL;
	}

	public Integer getStrength() {
		return strength;
	}

	public void setStrength(Integer strength) {
		this.strength = strength;
	}

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		if (gameState.getBoard().isEmpty(x, y))
			return 2;
		return 0;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		gameState.getBoard().getTraps(x, y).add(new model.traps.PitTrap(gameState, strength, new Pair<Integer, Integer>(x, y)));
	}

}
