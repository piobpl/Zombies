package model;

import java.util.List;

public class Hand {
	
	GameState gameState;
	Player player;
	List<Card> hand;
	
	public Hand(GameState gameState, Player player){
		this.gameState = gameState;
		this.player = player;
		fillHand();
	}
	
	public boolean fillHand(){
		while(hand.size()<4){
			try {
				hand.add(gameState.getDeck(player).getTopCard());
			} catch (NoMoreCardsException e) {
				System.err.println("Lack of cards on the deck of player: "+player);
				return false;
			}
		}
		return true;
	}
	
	public boolean removeCard(Card card){
		return hand.remove(card);
	}
	
}
