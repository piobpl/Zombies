package model;

import java.util.List;

public class Hand {
	
	GameState gameState;
	Player player;
	List<Card> hand;
	
	public Hand(GameState gameState, Player player){
		this.gameState = gameState;
		this.player = player;
	}
	
	public boolean removeCard(Card card){
		return hand.remove(card);
	}
	
}
