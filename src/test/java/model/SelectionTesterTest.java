package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import utility.Pair;

public class SelectionTesterTest {

	@Test
	public void test() {
		List<Pair<Integer, Integer>> selection = new ArrayList<>();
		selection.add(new Pair<Integer, Integer>(0,0));
		selection.add(new Pair<Integer, Integer>(0,1));
		selection.add(new Pair<Integer, Integer>(0,2));
		selection.add(new Pair<Integer, Integer>(1,0));
		selection.add(new Pair<Integer, Integer>(1,1));
		selection.add(new Pair<Integer, Integer>(2,0));
		assertEquals(SelectionTester.areEdgeSolid(selection), true);
		selection.add(new Pair<Integer, Integer>(2,2));
		assertEquals(SelectionTester.areEdgeSolid(selection), false);
		selection.add(new Pair<Integer, Integer>(2,1));
		assertEquals(SelectionTester.areEdgeSolid(selection), true);
	}

}
