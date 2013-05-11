package controller;

import java.util.List;

import controller.Selection.GroupSelection;
import utility.Pair;
/**
 * Class for test use only
 * @author jerzozwierz
 *
 */
public class ForTestsOnly {
	
	public static GroupSelection getNewGroupSelection(List<Pair<Integer, Integer>> cells) {
		return new GroupSelection(cells);
	}
	
}
