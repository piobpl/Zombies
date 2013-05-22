package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.CellSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.SelectionTester;
import game.model.traps.WallTrap;
import utility.Pair;
/**
 * 
 * @author Edoipi
 *
 */
public class Wall extends Card {
	Integer height;

	public Wall(Integer height) {
		this.height = height;
	}

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Pair<Integer, Integer> p = ((CellSelection) selection).cell;
		if (p.first == 4)
			return 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				if (SelectionTester.areVertexAdjacent(p,
						new Pair<Integer, Integer>(i, j))) {
					if (gameState.getBoard().is(i, j, CardType.ZOMBIE)
							|| gameState.getBoard().is(i, j, CardType.DOGS)) {
						return 0;
					}
				}
			}
		}
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Pair<Integer, Integer> p = ((CellSelection) selection).cell;
		gameState.getBoard().getTraps(p.first, p.second)
				.add(new WallTrap(gameState, height, p));
	}

	@Override
	public String getName() {
		return "Wall";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.CELL;
	}

	@Override
	public Integer getStrength() {
		return height;
	}

	@Override
	public void setStrength(Integer strength) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public CardType getType() {
		return CardType.WALL;
	}

	@Override
	public String getTooltipMessage() {
		return "You can play wall only on an empty square, and only if there is no zombie on an adjacent square (vertically, horizontally or diagonally). Also, you cannot place a wall behind any zombie’s back or, to be more precise, any rows behind a zombie are off your limits as far as using this card is concerned. You cannot put the wall on the fifth row. Once put, the wall remains there until the end of the game, unless it is destroyed by the pick axe or grenade. The wall can be as high as 5 or 6, which is displayed on the card. The wall blocks the way through the square on which it stands: dogs cannot move over the wall, the rolling barrel crashes into it, zombies must stop on the square directly before the wall if their total strength is smaller than the wall’s height. They may form a line of zombies, one after another. Then, the zombies at the back can “push” the front zombie onto the wall but ONLY if their total strength equals or exceeds the wall’s height.";
	}
}