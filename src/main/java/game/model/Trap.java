package game.model;

import java.io.Serializable;
import java.util.EnumSet;

import utility.Pair;
import utility.Typed;

public abstract class Trap implements Typed<Trap.TrapType>, Serializable {

	private static final long serialVersionUID = -4536856118237872330L;

	public static enum Trigger {
		FIRE, SHOT, EXPLOSION, VOLTAGE, HOLLOW;
	}

	public static enum TrapType {
		BARRIER, CAR, MINE, PIT, WALL, NAPALM;
	}

	public abstract String getName();

	public abstract int getTime();

	public abstract void decreaseTime();

	public abstract boolean isMovePossible(Card card,
			Pair<Integer, Integer> from);

	public abstract void movedOn(Card card);

	public abstract EnumSet<Trigger> getTriggers();

	public abstract void trigger();
}
