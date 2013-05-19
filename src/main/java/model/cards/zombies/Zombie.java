package model.cards.zombies;

import model.Card;
import model.GameState;
import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public class Zombie extends Card {

	private Integer strength;

	public Zombie(Integer strength) {
		this.strength = strength;
	}

	public String getName() {
		return "Zombie";
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
		if (gameState.getBoard().isEmpty(x, y) && x == 0)
			return 2;
		return 0;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		gameState.getBoard().set(x, y, this);
	}

	@Override
	public CardType getType() {
		return CardType.ZOMBIE;
	}
}
