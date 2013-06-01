package game.model.cards.zombies;

import game.controller.Selection;
import game.controller.Selection.GroupSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.Modifier;
import game.model.Modifier.ModifierType;
import game.model.MoveMaker;
import game.model.SelectionTester;

import java.util.List;

import utility.Pair;
/**
 * 
 * @author michal
 *
 */
public class BossMover extends Card {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4494245466288635858L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		if(cells.size() > 2)
			return 0;
		if(cells.isEmpty())
			return 1;
		Card card = gameState.getBoard().get(cells.get(0).first, cells.get(0).second);
		if(card == null || card.getType() != CardType.ZOMBIE || card.getStrength() > 3 
				|| card.getModifiers().contains(ModifierType.COMMANDEDBYBOSS))
			return 0;
		if(cells.size() == 1)
			return 1;
		if(SelectionTester.areEdgeAdjacent(cells.get(0), cells.get(1)) &&
				MoveMaker.isMovePossible(gameState, cells.get(0), cells.get(1), card))
			return 2;
		return 0;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		List<Pair<Integer, Integer>> cells = ((GroupSelection) selection).cells;
		Card card = gameState.getBoard().get(cells.get(0).first, cells.get(0).second);
		card.getModifiers().add(new Modifier(ModifierType.COMMANDEDBYBOSS, Integer.MAX_VALUE));
		MoveMaker.moveTo(gameState, cells.get(0), cells.get(1));
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.GROUP;
	}

	@Override
	public Integer getStrength() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStrength(Integer strength) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CardType getType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTooltipMessage() {
		throw new UnsupportedOperationException();
	}

}
