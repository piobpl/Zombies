package controller;

import java.util.List;

import model.Player;
import utility.Pair;

public abstract class Selection {
	public static enum SelectionType {
		CELL, COLUMN, GROUP, HAND;
	}

	// TODO

	public static class CellSelection extends Selection {
		public final Pair<Integer, Integer> cell = null;
	}

	public static class ColumnSelection extends Selection {
		public final Integer column = 0;;
	}

	public static class GroupSelection extends Selection {
		public final List<Pair<Integer, Integer>> cells = null;
	}

	public static class HandSelection extends Selection {
		public final Player player = null;
		public final Integer card = null;
	}
}
