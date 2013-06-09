package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import utility.Pair;
/**
 *
 * @author Edoipi
 *
 */
public class Car extends Card {

	
	private static final long serialVersionUID = 7988782437542268453L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		int x = ((CellSelection) selection).cell.first;
		int y = ((CellSelection) selection).cell.second;

		for (int i = x; i < 5; i++) {
			if (!gameState.getBoard().isCompletelyEmpty(i, y)) {
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
				.add(new game.model.traps.CarTrap(gameState,
						new Pair<Integer, Integer>(x, y)));
	}

	@Override
	public String getName() {
		return "Car";
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
		return CardType.CAR;
	}

	@Override
	public String getTooltipMessage() {
		return "<html>You can put a booby-trapped car on any square, if there aren't any obstacles (like zombies) on given lane for the car to drive to that square.<br>You can set the car off with any kind of shot (shot, burst or sniper shot), fire (napalm, gasoline, flamethrower, street on fire) or explosion (grenade, mine shrapnel).<br>Zombies and dogs can walk onto and over the car.<br>Exploding car deals one damage point to the square it is standing on, and one damage point to each adjacent square (even diagonally).</html>";
	}
}
