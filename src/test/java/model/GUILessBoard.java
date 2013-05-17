package model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import model.cards.helpers.Card;
import model.traps.Trap;

public class GUILessBoard extends Board {

	public GUILessBoard(GameState gameState) {
		super(gameState);
		board = new Card[5][3];
		traps = new LinkedList<HashSet<Trap>>();
		for (int i = 0; i < 15; i++)
		traps.add(new HashSet<Trap>());
	}
	
	private Card[][] board;
	private List<HashSet<Trap>> traps;
	
	@Override
	public void remove(int x, int y) {
		board[x][y] = null;
	}

	@Override
	public void set(int x, int y, Card card) {
		board[x][y] = card;
	}

	@Override
	public void update(int x, int y) {

	}

	@Override
	public Card get(int x, int y) {
		return board[x][y];
	}

	@Override
	public boolean isEmpty(int x, int y) {
		return board[x][y] == null;
	}

	@Override
	public boolean isCompletelyEmpty(int x, int y) {
		return (board[x][y] == null && getTraps(x, y).isEmpty());
	}

	@Override
	public boolean is(int x, int y, String name) {
		return board[x][y] != null && board[x][y].getName() == name;
	}

	@Override
	public void exchangeContent(int x1, int y1, int x2, int y2) {
		Card tmpCard = get(x1, y1);
		HashSet<Trap> tmpTrapsSet = getTraps(x1, y1);
		remove(x1, y1);
		set(x1, y1, get(x2, y2));
		getTraps(x1, y1).clear();
		getTraps(x1, y1).addAll(getTraps(x2, y2));
		remove(x2, y2);
		set(x2, y2, tmpCard);
		getTraps(x2, y2).clear();
		getTraps(x2, y2).addAll(tmpTrapsSet);
		update(x1, y1);
		update(x2, y2);
	}

	@Override
	public HashSet<Trap> getTraps(int x, int y) {
		return traps.get(x * 3 + y);
	}
	

}
