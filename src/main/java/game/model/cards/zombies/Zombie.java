package game.model.cards.zombies;

import utility.Pair;
import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.MoveMaker;

public class Zombie extends Card {

	/**
	 *
	 * @author michal
	 */
	private static final long serialVersionUID = -1967753225254322981L;
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
		//Integer y = ((CellSelection) selection).cell.second;
		Pair<Integer, Integer> tmp=((CellSelection) selection).cell;
		if (MoveMaker.isMovePossible(gameState, tmp, tmp, this) && x.equals(0))
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
