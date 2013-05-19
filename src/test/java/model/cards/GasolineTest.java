package model.cards;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import model.cards.humans.Gasoline;

import org.junit.Test;

import utility.Pair;
import controller.ForTestsOnly;
import controller.Selection.GroupSelection;

public class GasolineTest {

	@Test
	public void test() {
		List<Pair<Integer, Integer>> list = new LinkedList<>();
		Gasoline g = new Gasoline();
		Pair<Integer, Integer> temp1 = new Pair<Integer, Integer>(1, 1);
		Pair<Integer, Integer> temp2 = new Pair<Integer, Integer>(1, 2);
		Pair<Integer, Integer> temp3 = new Pair<Integer, Integer>(2, 3);
		// Pair<Integer, Integer> temp4 = new Pair<Integer, Integer> ();
		// Pair<Integer, Integer> temp5 = new Pair<Integer, Integer> ();
		list.add(temp1);
		list.add(temp2);
		list.add(temp3);
		GroupSelection gs = ForTestsOnly.getNewGroupSelection(list);
		assertEquals(0, g.rateSelection(null, gs));
		temp3.first = 1;
		assertEquals(2, g.rateSelection(null, gs));

	}

}
