package model.cards.helpers;

import utility.Pair;

/**
 * Klasa wspomagająca sprawdzanie selekcji dla kart.
 *
 * @author piob
 */
public abstract class SolidityTester {

	public static Pair<Integer, Integer> getDist(Pair<Integer, Integer> pos1,
			Pair<Integer, Integer> pos2) {
		return new Pair<Integer, Integer>(pos2.first - pos1.first, pos2.second
				- pos1.second);
	}

	public static boolean areEdgeAdjacent(Pair<Integer, Integer> pos1,
			Pair<Integer, Integer> pos2) {
		Pair<Integer, Integer> dist = getDist(pos1, pos2);
		return Math.abs(dist.first) + Math.abs(dist.second) == 1;
	}

	public static boolean areVertexAdjacent(Pair<Integer, Integer> pos1,
			Pair<Integer, Integer> pos2) {
		Pair<Integer, Integer> dist = getDist(pos1, pos2);
		return Math.max(Math.abs(dist.first), Math.abs(dist.second)) == 1;
	}

	public static boolean areInSameRow(Pair<Integer, Integer> pos1,
			Pair<Integer, Integer> pos2) {
		return pos1.first == pos2.first;
	}
}
