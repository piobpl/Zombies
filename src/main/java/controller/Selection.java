package controller;

public abstract class Selection {
	public static enum SelectionType {
		CELL, COLUMN, GROUP, HAND;
	}

	// TODO

	public static class CellSelection extends Selection {
	}

	public static class ColumnSelection extends Selection {
	}

	public static class GroupSelection extends Selection {
	}

	public static class HandSelection extends Selection {
	}
}
