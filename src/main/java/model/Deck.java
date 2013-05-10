package model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

class NoMoreCardsException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6892455103503069575L;

	public NoMoreCardsException() {
	}

	public NoMoreCardsException(String s) {
		super(s);
	}

	public NoMoreCardsException(String s, Throwable t) {
		super(s, t);
	}

	public NoMoreCardsException(Throwable t) {
		super(t);
	}
}

public class Deck {

	private List<Card> deck;

	public Deck(Player player) {
		deck = getShuffledDeck(player); //TODO czy tam jest karta swit? zapytac implementatorow kart
	}
	
	public Card getTopCard() throws NoMoreCardsException{
		if(deck.isEmpty()){
			throw new NoMoreCardsException();
		}
		return deck.remove(0);
	}
	
	public int getDeckSize(){
		return deck.size();
	}

	public static List<Card> getShuffledDeck(Player player) {
		return getShuffledDeck(player, null);
	}

	public static List<Card> getShuffledDeck(Player player, Random rnd) {
		if (rnd == null) {
			rnd = new Random();
		}
		List<Card> Ltmp = Card.getCompleteDeck(player);
		Collections.shuffle(Ltmp, rnd);
		return Ltmp;
	}
}
