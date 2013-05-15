package model;

import utility.Pair;

public abstract class Trap {
	public abstract String getName();
	public abstract int getTime();
	public abstract void decreaseTime();
	public abstract boolean isMovePossible(Card card, Pair<Integer, Integer> from);
	public abstract void movedOn(Card card);
	public abstract void shot();
	public abstract void fire();
	public abstract void blowUp();
}
