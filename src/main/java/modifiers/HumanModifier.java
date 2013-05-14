package modifiers;

import model.Modifier;

public class HumanModifier extends Modifier {

	private int time;
	
	public HumanModifier(int time) {
		this.time = time;
	}
	
	@Override
	public String getName() {
		return "Human";
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
		return "holds a human";
	}

}
