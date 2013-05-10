package model;

import java.util.LinkedList;
import java.util.List;

public class Board {
	
	private List<Card>[][] board;
	
	@SuppressWarnings("unchecked")
	public Board(){
		board = new LinkedList[5][3];
	}

	public boolean removeCardAtPosition(int x, int y, Card card){
		return board[x][y].remove(card);
	}
	
	public boolean addCardAtPosition(int x, int y, Card card){
	//	if(board[x][y]!=null){
			//TODO czy mam tu sprawdzac czy mozna polozyc?
		//	return false;
		//}else{
			board[x][y].add(card);
			return true;
		//}
	}
	
	public List<Card> getCardsAtPosition(int x, int y){
		return board[x][y];
	}
	
}
