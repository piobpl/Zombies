package model;

import game.model.Board;
import game.model.Card;
import game.model.GameState;
import game.model.Trap;
import game.model.Card.CardType;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


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
	public boolean is(int x, int y, CardType type) {
		return board[x][y] != null && board[x][y].getType() == type;
	}

}
