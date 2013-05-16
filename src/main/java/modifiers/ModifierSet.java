package modifiers;

import java.util.HashSet;

public class ModifierSet {
	private HashSet<Modifier> set = new HashSet<Modifier>();
	
	public void add(Modifier modifier) {
		set.add(modifier);
	}
	
	public boolean contains(ModifierType modifierType) {
		for(Modifier m : set)
			if(m.modifierType == modifierType) return true;
		return false;
	}
}
