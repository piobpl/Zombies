package controller;

import java.util.ArrayList;
import java.util.List;

import model.Player;
import utility.Pair;

public abstract class Selection {
	public static enum SelectionType {
		CELL, COLUMN, GROUP, HAND;
	}

	public Selection add(Pair<Integer, Integer> p) {
		return this;
	}

	public static class CellSelection extends Selection {
		public final Pair<Integer, Integer> cell;

		public CellSelection() {
			cell = null;
		}

		public CellSelection(Pair<Integer, Integer> cell) {
			this.cell = cell;
		}

		public Selection add(Pair<Integer, Integer> p) {
			return new CellSelection(p);
		}
	}

	public static class ColumnSelection extends Selection {
		public final Integer column;

		public ColumnSelection() {
			column = 0;
		}

		public ColumnSelection(Integer column) {
			this.column = column;
		}

		public Selection add(Pair<Integer, Integer> p) {
			return new ColumnSelection(p.second);
		}
	}

	public static class GroupSelection extends Selection {
		public final List<Pair<Integer, Integer>> cells;

		public GroupSelection() {
			cells = new ArrayList<>();
		}

		public GroupSelection(List<Pair<Integer, Integer>> cells) {
			this.cells = cells;
		}

		public Selection add(Pair<Integer, Integer> p) {
			List<Pair<Integer, Integer>> l = new ArrayList<>(cells);
			if (!l.remove(p))
				l.add(p);
			return new GroupSelection(l);
		}
	}

	public static class HandSelection extends Selection {
		public final Player player;
		public final Integer card;

		HandSelection() {
			player = null;
			card = null;
		}

		HandSelection(Player player, Integer card) {
			this.player = player;
			this.card = card;
		}
	}
}
