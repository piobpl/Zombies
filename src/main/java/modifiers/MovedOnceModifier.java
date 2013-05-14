package modifiers;

import model.Modifier;

public class MovedOnceModifier extends Modifier {

	private int time;
	
	public MovedOnceModifier(int time) {
		this.time = time;
	}
	
	@Override
	public String getName() {
		return "MovedOnce";
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public void decreaseTime() {
		time--;
	}

	@Override
	public String getDescription() {
		return "moved once this turn";
	}

}
