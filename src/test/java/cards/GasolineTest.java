package cards;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import controller.Selection.GroupSelection;
import cards.humans.Gasoline;
import controller.ForTestsOnly;
import utility.Pair;

public class GasolineTest {

	@Test
	public void test() {
		List<Pair<Integer, Integer>> list = new LinkedList<>();
		Gasoline g = new Gasoline();
		Pair<Integer, Integer> temp1 = new Pair<Integer, Integer> ();
		Pair<Integer, Integer> temp2 = new Pair<Integer, Integer> ();
		Pair<Integer, Integer> temp3 = new Pair<Integer, Integer> ();
		//Pair<Integer, Integer> temp4 = new Pair<Integer, Integer> ();
		//Pair<Integer, Integer> temp5 = new Pair<Integer, Integer> ();
		temp1.first = 1; temp1.second = 1; list.add(temp1);
		temp2.first = 1; temp2.second = 2; list.add(temp2);
		temp3.first = 2; temp3.second = 3; list.add(temp3);
		GroupSelection gs = ForTestsOnly.getNewGroupSelection(list);
		assertEquals(false, g.isSelectionCorrect(null, gs));
		temp3.first = 1;
		assertEquals(true, g.isSelectionCorrect(null, gs));
		
	}

}
