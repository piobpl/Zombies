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
	
	// potrzebne do usuwania człowieka
	public void remove(ModifierType modifierType) {
		Modifier toRemove = null;
		for(Modifier m : set)
			if(m.modifierType == modifierType) {
				toRemove = m;
				break;
			}
		if(toRemove != null)
			set.remove(toRemove);
	}
}
