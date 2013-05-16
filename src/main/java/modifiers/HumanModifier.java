package modifiers;

import model.Modifier;

public class HumanModifier extends Modifier {

	public HumanModifier() {}
	
	@Override
	public String getName() {
		return "Human";
	}

	@Override
	public int getTime() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void decreaseTime() {}

	@Override
	public String getDescription() {
		return "holds a human";
	}

}
