package cards.helpers;

import utility.Pair;

public abstract class SolidityTester {

	public static boolean areEdgeAdjacent(Pair<Integer, Integer> pos1, Pair<Integer, Integer> pos2) {
		if (pos1.first.equals(pos2.first) && Math.abs(pos1.second - pos2.second) == 1)
			return true;
		if (pos1.second.equals(pos2.second) && Math.abs(pos1.first - pos2.first) == 1)
			return true;
		return false;
	}
	
	public static boolean areInSameRow(Pair<Integer, Integer> pos1, Pair<Integer, Integer> pos2){
		return pos1.first == pos2.first;
	}
}
