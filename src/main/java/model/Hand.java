package model;

import java.util.LinkedList;
import java.util.List;

/**
 * A class representing a hand.
 */
public class Hand {

	private List<Card> hand;

	/**
	 * Creates a new empty hand.
	 */
	public Hand() {
		hand = new LinkedList<Card>();
	}

	/**
	 * Returns a list representing a hand.
	 * 
	 * @return a hand
	 */
	public List<Card> getHand() {
		return hand;
	}

	/**
	 * Clears hand.
	 * 
	 */
	public void clearHand() {
		hand.clear();
	}

	/**
	 * Adds a card to the hand.
	 * 
	 * @param card
	 *            a card to be added
	 */
	public void addCard(Card card) {
		hand.add(card);
	}

	/**
	 * Removes a card from the hand.
	 * 
	 * @param card
	 *            a card to be removed
	 */
	public boolean removeCard(Card card) {
		return hand.remove(card);
	}

	/**
	 * Checks whether the hand is empty.
	 * 
	 * @return true if the hand is empty
	 */
	public boolean isEmpty() {
		return hand.isEmpty();
	}

}
