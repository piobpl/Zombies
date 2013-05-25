package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;

public class Dogs extends Card {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5291194950732662660L;
	private Integer strength = 1;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		if (gameState.getBoard().isEmpty(x, y) && x.equals(0))
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

	@Override
	public CardType getType() {
		return CardType.DOGS;
	}

	@Override
	public String getTooltipMessage() {
		return "You play dogs onto one of the three first-row squares, providing another zombie, dog or barrier does not block that square. Dogs move at the beginning of zombies’ phase, providing their move is possible (there are no movement-blocking cards on the board). Dogs can move three squares per phase, in any direction except diagonally. They cannot move through squares occupied by zombies or other dogs.";
	}

}
