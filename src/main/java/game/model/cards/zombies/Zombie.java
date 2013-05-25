package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;

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

	@Override
	public String getTooltipMessage() {
		return "<html>You play zombies onto one of the three first-row squares, providing another zombie, dog or barrier does not block that square.<br>There can be only one zombie or dog on a single square.<br>In the beginning of zombies phase every zombie on the board must move one square forward (of course, if the forward movement is possible).</html>";
	}
}
