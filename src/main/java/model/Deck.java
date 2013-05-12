package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * A class representing a deck.
 */
public class Deck {

	private List<Card> deck;
	private Player player;
	private GameState gameState;
	
	/**
	 * Creates a new shuffled deck for the specified player.
	 * 
	 * @param player
	 *            a player whose deck is to be created
	 */
	public Deck(GameState gameState, Player player) {
		this.gameState = gameState;
		this.player = player;
		deck = getShuffledDeck(player);
		gameState.gui.setCardsLeft(player, deck.size());
	}

	private List<Card> getShuffledDeck(Player player) {
		return getShuffledDeck(player, null);
	}

	private List<Card> getShuffledDeck(Player player, Random rnd) {
		if (rnd == null) {
			rnd = new Random();
		}
		List<Card> Ltmp = Card.getCompleteDeck(player);
		Collections.shuffle(Ltmp, rnd);
		return Ltmp;
	}

	/**
	 * Returns a card from the top of the deck( and removes it ). Returns null
	 * if the deck is empty.
	 * 
	 * @return a card from the top of the deck or null
	 */
	public Card getTopCard() {
		if (isEmpty()) {
			return null;
		}
		gameState.gui.setCardsLeft(player, deck.size()-1);
		return deck.remove(0);
	}

	/**
	 * Returns a list of cards from the top of the deck ( and removes them ).
	 * Returns null if there are not enough cards in the deck.
	 * 
	 * @param i
	 *            the number of cards to be taken from the deck
	 * 
	 * @return a list of cards from the top of the deck or null
	 */
	public List<Card> get(int i) {
		if (i > getDeckSize()) {
			return null;
		} else {
			List<Card> tmp = new LinkedList<>();
			while (i > 0) {
				gameState.gui.setCardsLeft(player, deck.size()-1);
				tmp.add(deck.remove(0));
				i--;
			}
			return tmp;
		}
	}

	/**
	 * Removes a specified number of cards from the top of the deck. If the
	 * given number is bigger than or equals the size of the deck then the deck
	 * will be empty after the call of this method.
	 * 
	 * @param i
	 *            the number of cards to be removed
	 */
	public void remove(int i) {
		while (i > 0 && !isEmpty()) {
			gameState.gui.setCardsLeft(player, deck.size()-1);
			deck.remove(0);
			i--;
		}
	}

	/**
	 * Returns the current size of the deck.
	 * 
	 * @return a size of the deck
	 */
	public int getDeckSize() {
		return deck.size();
	}

	/**
	 * Checks whether the deck is empty.
	 * 
	 * @return true if the deck is empty
	 */
	public boolean isEmpty() {
		return deck.isEmpty();
	}

}
