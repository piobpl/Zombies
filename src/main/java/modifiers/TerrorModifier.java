package modifiers;

import model.Modifier;

public class TerrorModifier extends Modifier {

	private int time;
	
	public TerrorModifier(int time) {
		this.time = time;
	}
	
	@Override
	public String getName() {
		return "Terror";
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
		return "Terror";
	}

}
