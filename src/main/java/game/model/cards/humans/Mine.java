package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import utility.Pair;

/**
 * 
 * @author krozycki
 *
 */
public class Mine extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;
		if (!gameState.getBoard().isCompletelyEmpty(x, y)) {
			return 0;
		}
		if (x > 0) {
			if (!gameState.getBoard().isEmpty(x - 1, y)
					&& (gameState.getBoard().get(x - 1, y).getType() == CardType.ZOMBIE || gameState.getBoard()
							.get(x - 1, y).getType() == CardType.DOGS)) {
				return 0;
			}
		}
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer x = ((CellSelection) selection).cell.first;
		Integer y = ((CellSelection) selection).cell.second;
		gameState
				.getBoard()
				.getTraps(x, y)
				.add(new game.model.traps.MineTrap(gameState,
						new Pair<Integer, Integer>(x, y)));
	}

	@Override
	public String getName() {
		return "Mine";
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
		return CardType.MINE;
	}

	@Override
	public String getTooltipMessage() {
		return "You can put it on any square, save those directly before any zombies or dogs. If a zombie, dog or barrel moves onto a mined square, the mine sets off. A mine can be also set off by fire (flamethrower, gasoline, street on fire, napalm) or explosion (grenade, car). A zombie or dog standing on a mined square takes 2 points of damage, and one adjacent square, chosen by the human player receives shrapnel damage equal to 1 point of damages.";
	}

}
