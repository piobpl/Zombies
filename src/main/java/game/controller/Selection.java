package game.controller;

import game.model.Player;

import java.util.ArrayList;
import java.util.List;

import utility.Pair;

/**
 *
 * @author Edoipi
 *
 */
public abstract class Selection {
	public static enum SelectionType {
		CELL, COLUMN, GROUP, MULTIGROUP, HAND, EMPTY, UNPLAYABLE;
	}

	public abstract Selection add(Pair<Integer, Integer> p);

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

	public static class MultiGroupSelection extends Selection {
		public final List<Pair<Integer, Integer>> cells;

		public MultiGroupSelection() {
			cells = new ArrayList<>();
		}

		public MultiGroupSelection(List<Pair<Integer, Integer>> cells) {
			this.cells = cells;
		}

		public Selection add(Pair<Integer, Integer> p) {
			List<Pair<Integer, Integer>> l = new ArrayList<>(cells);
			l.add(p);
			return new MultiGroupSelection(l);
		}

		public Selection remove(Pair<Integer, Integer> p) {
			List<Pair<Integer, Integer>> l = new ArrayList<>(cells);
			l.remove(p);
			return new MultiGroupSelection(l);
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

		public Selection add(Pair<Integer, Integer> p) {
			throw new UnsupportedOperationException();
		}
	}

	public static class EmptySelection extends Selection {

		@Override
		public Selection add(Pair<Integer, Integer> p) {
			throw new UnsupportedOperationException();
		}

	}

}
