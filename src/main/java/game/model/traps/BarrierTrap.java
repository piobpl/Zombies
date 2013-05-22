package game.model.traps;

import game.model.Card;
import game.model.Trap;

import java.util.EnumSet;

import utility.Pair;

/**
 * Trap card. Remember to put Barriers into a whole column. Always. Barrier
 * absorbs shots. This card has no need to know its coordinates nor gameState.
 *
 * @author jerzozwierz
 *
 */
public class BarrierTrap extends Trap {

	private int time;

	public BarrierTrap(int time) {
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
		throw new UnsupportedOperationException();
	}

	@Override
	public EnumSet<Trigger> getTriggers() {
		return EnumSet.of(Trigger.SHOT);
	}

	@Override
	public void trigger() {
	}

	@Override
	public TrapType getType() {
		return TrapType.BARRIER;
	}

}
