package model;

import java.util.EnumSet;

import utility.Pair;

public abstract class Trap {
	public static enum Trigger{
		FIRE, SHOT, EXPLOSION, VOLTAGE;
	}
	public abstract String getName();
	public abstract int getTime();
	public abstract void decreaseTime();
	public abstract boolean isMovePossible(Card card, Pair<Integer, Integer> from);
	public abstract void movedOn(Card card);
	public abstract EnumSet<Trigger> getTriggers();
	public abstract void trigger();
}
