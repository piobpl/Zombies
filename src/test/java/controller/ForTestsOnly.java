package controller;

import game.controller.Selection.CellSelection;
import game.controller.Selection.ColumnSelection;
import game.controller.Selection.GroupSelection;

import java.util.List;

import utility.Pair;

/**
 * Class for test use only
 *
 * @author jerzozwierz
 *
 */
public class ForTestsOnly {

	public static GroupSelection getNewGroupSelection(
			List<Pair<Integer, Integer>> cells) {
		return new GroupSelection(cells);
	}

	public static ColumnSelection getNewColumnSelection(Integer column) {
		return new ColumnSelection(column);
	}

	public static CellSelection getNewCellSelection(Integer x, Integer y) {
		return new CellSelection(new Pair<Integer, Integer>(x, y));
	}

}
