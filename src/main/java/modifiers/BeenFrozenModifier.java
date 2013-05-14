package modifiers;

import model.Modifier;

public class BeenFrozenModifier extends Modifier {

	private int time;
	
	public BeenFrozenModifier(int time) {
		this.time = time;
	}
	
	@Override
	public String getName() {
		return "BeenFrozen";
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
		return "can't move this turn";
	}

}
