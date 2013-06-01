package game.model;

import java.io.Serializable;

import utility.Typed;
import utility.TypedSet;

/**
 * W konstruktorze podajemy element enuma ModifierType, np. ModifierType.HUMAN i
 * czas działania.
 *
 * @author michal
 *
 */

public class Modifier implements Typed<Modifier.ModifierType>, Serializable {

	private static final long serialVersionUID = -3659762201122440620L;

	/**
	 * typy modyfikatorów, getDescription() zwraca opis
	 *
	 * @author michal
	 *
	 */
	public enum ModifierType {
		HUMAN {
			public String getDescription() {
				return "human-shield";
			}
		},
		FROZEN {
			public String getDescription() {
				return "frozen";
			}
		},
		MOVEDONCE {
			public String getDescription() {
				return "advanced";
			}
		},
		TERROR {
			public String getDescription() {
				return "terror";
			}
		},
		BOSS {
			public String getDescription() {
				return "boss";
			}
		},
		COMMANDEDBYBOSS {
			public String getDescription() {
				return "commanded by Boss";
			}
		},
		GLOBALBOSS {
			public String getDescription() {
				return "Boss is here";
			}
		};
		public abstract String getDescription();
	}

	private ModifierType modifierType;
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
		return modifierType.getDescription();
	}

	@Override
	public ModifierType getType() {
		return modifierType;
	}

	public static void nextStage(TypedSet<Modifier, ModifierType> modifiers) {
		TypedSet<Modifier, ModifierType> backup = new TypedSet<>();
		for (Modifier m : modifiers) {
			backup.add(m);
		}
		for (Modifier m : backup) {
			m.decreaseTime();
			if (m.getTime() <= 0)
				modifiers.remove(m);
		}
	}
}
