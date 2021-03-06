package game.model;

import game.model.Card.CardType;
import game.model.Trap.TrapType;

import java.util.List;


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

	public static int getStrength(GameState gameState,
			List<Pair<Integer, Integer>> selection) {
		int sum = 0;
		for (Pair<Integer, Integer> p : selection) {
			Card c = gameState.getBoard().get(p.first, p.second);
			if (c == null)
				sum += 1;
			else if(c.getType() == CardType.ZOMBIE || c.getType() == CardType.DOGS)
				sum += c.getStrength();
		}
		return sum;
	}

	public static int getFarthestZombieRow(GameState gameState){
		for(int x = 4; x > -1; --x)
			for(int y = 0; y < 3; ++y){
				Card c = gameState.getBoard().get(x, y);
				if(c != null && (c.getType() == CardType.ZOMBIE || c.getType() == CardType.DOGS))
					return x;
			}
		return -1;
	}

	public static boolean isBehindWall(GameState gameState, int x, int y){
		while(++x < 5){
			if(gameState.getBoard().getTraps(x, y).contains(TrapType.WALL))
				return true;
		}
		return false;
	}
}
