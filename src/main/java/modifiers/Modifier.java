package modifiers;


public class Modifier {
	public final ModifierType modifierType;
	private int time;
	
	public Modifier(ModifierType modifierType, int time) {
		this.modifierType = modifierType;
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}

	public void decreaseTime() {
		time--;
	}
	
	public String getName() {
		return modifierType.getName();
	}
}
