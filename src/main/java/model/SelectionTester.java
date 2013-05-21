package model;

import java.util.List;

import model.Card.CardType;

import utility.Pair;

/**
 * Klasa wspomagająca sprawdzanie selekcji dla kart.
 *
 * @author piob
 */
public abstract class SelectionTester {

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

	private static final int[] dx = { -1, 1, 0, 0 };
	private static final int[] dy = { 0, 0, -1, 1 };

	private static boolean in(int x, int y) {
		return 0 <= x && x < 5 && 0 <= y && y < 3;
	}

	private static void dfs(int x, int y, boolean[][] selected,
			boolean[][] visited) {
		if (visited[x][y])
			return;
		visited[x][y] = true;
		for (int i = 0; i < 4; ++i)
			if (in(x + dx[i], y + dy[i]) && selected[x + dx[i]][y + dy[i]])
				dfs(x + dx[i], y + dy[i], selected, visited);
	}

	public static boolean areEdgeSolid(List<Pair<Integer, Integer>> selection) {
		if (selection.isEmpty())
			return true;
		boolean[][] selected = new boolean[5][3];
		boolean[][] visited = new boolean[5][3];
		for (Pair<Integer, Integer> p : selection)
			selected[p.first][p.second] = true;
		dfs(selection.get(0).first, selection.get(0).second, selected, visited);
		for (Pair<Integer, Integer> p : selection)
			if (!visited[p.first][p.second])
				return false;
		return true;
	}

	public static int getZombiesStrength(GameState gameState,
			List<Pair<Integer, Integer>> selection) {
		int sum = 0;
		for (Pair<Integer, Integer> p : selection) {
			Card c = gameState.getBoard().get(p.first, p.second);
			if (c != null && c.getType() == CardType.ZOMBIE)
				sum += c.getStrength();
		}
		return sum;
	}
}
