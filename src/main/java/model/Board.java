package model;

public class Board {
	
	private Card[][] board;
	
	public Board(){
		board = new Card[5][3];
	}

	public void remove(int x, int y){
		board[x][y] = null;
	}
	
	public void set(int x, int y, Card card){
		board[x][y] = card;
	}
	
	public Card get(int x, int y){
		return board[x][y];
	}
	
	public boolean isEmpty(int x, int y) {
		return board[x][y] == null;
	}
	
}
