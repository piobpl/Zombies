package model.traps;

import java.util.EnumSet;

import model.cards.helpers.Card;
import utility.Pair;
/**
 * Trap card.
 * Remember to put Barriers into a whole column. Always.
 * Barrier absorbs shots.
 * This card has no need to know its coordinates nor gameState.
 * @author jerzozwierz
 *
 */
public class BarrierTrap extends Trap {

	private int time = 4; //i guess
	//jak jest co ustawiac to pozwolmy:
	public BarrierTrap(int time){
		this.time = time;
	}

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
