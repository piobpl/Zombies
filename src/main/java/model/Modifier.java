package model;

public abstract class Modifier {
	public abstract String getName();
	public abstract String getDescription();
	public abstract int getTime();
	public abstract void decreaseTime();
}
