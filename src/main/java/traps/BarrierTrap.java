package traps;

import java.util.EnumSet;

import model.Card;
import model.Trap;
import utility.Pair;
/**
 * Trap card.
 * Remember to put Barriers into a whole column. Always.
 * Barrier absorbs shots.
 * This card has no need to know its coordinates nor gameState.
 * @author jerzozwierz
 *
 */
public class Barrier extends Trap {
	
	private int time = 4; //i guess
	
	@Override
	public String getName() {
		return "Barrier";
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public void decreaseTime() {
		--time;
	}

	@Override
	public boolean isMovePossible(Card card, Pair<Integer, Integer> from) {
		return false;
	}

	@Override
	public void movedOn(Card card) {
		assert true; //if you got here, something bad has happened
	}

	@Override
	public EnumSet<Trigger> getTriggers() {
		return EnumSet.of(Trigger.SHOT);
	}

	@Override
	public void trigger() {}

}
