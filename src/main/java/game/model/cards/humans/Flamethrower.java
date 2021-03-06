package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.MultiGroupSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.DamageDealer;
import game.model.GameState;
import game.model.SelectionTester;
import game.model.Trap.Trigger;

import java.util.List;

import utility.Pair;

/**
 * @author piob
 */
public class Flamethrower extends Card {

	
	private static final long serialVersionUID = 6438987840937959001L;

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		List<Pair<Integer, Integer>> cells = ((MultiGroupSelection) selection).cells;
		int row = SelectionTester.getFarthestZombieRow(gameState);
		if (row == -1)
			return 0;
		if (!SelectionTester.areEdgeSolid(cells))
			return 0;
		for (Pair<Integer, Integer> c : cells){
			if (SelectionTester.isBehindWall(gameState, c.first, c.second))
				return 0;
			if(c.first != row)
				return 0;
		}
		boolean isSelected[] = new boolean[3];
		int selectedCount = 0;
		for(Pair<Integer, Integer> cell : cells)
			isSelected[cell.second] = true;
		for(boolean b : isSelected)
			if(b) selectedCount++;
		if (cells.size() + 3 - selectedCount > 5)
			return 0;
		if (cells.size() < 5)
			return 1;
		return 2;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		for (Pair<Integer, Integer> p : ((MultiGroupSelection) selection).cells)
			DamageDealer.dealDamage(gameState, p.first, p.second, 1,
					Trigger.FIRE);
	}

	@Override
	public String getName() {
		return "Flamethrower";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.MULTIGROUP;
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
		return CardType.FLAMETHROWER;
	}

	@Override
	public String getTooltipMessage() {
		return "<html>The flamethrower has a power of 5.<br>It can target the first row having a zombie or dog standing in it, dealing a total of 5 damage points<br>(distributed by the whim of the human player, but concerning the rule that each empty square wastes one point of damage).<br> You cannot ignite a target hidden behind a wall.</html>";
	}
}
