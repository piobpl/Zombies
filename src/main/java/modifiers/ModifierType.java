package modifiers;

public enum ModifierType {
	HUMAN {
		public String getName() {
			return "holds a human";
		}
	},
	BEENFROZEN {
		public String getName() {
			return "can't move this turn";
		}
	},
	MOVEDONCE {
		public String getName() {
			return "can't move this turn";
		}
	},
	TERROR {
		public String getName() {
			return "Terror";
		}
	};
	public abstract String getName();
}
