package game.model;

import java.io.Serializable;

/**
 * A class representing a hand.
 */
public class Hand implements Serializable {

	private static final long serialVersionUID = -2423643842988295198L;
	private Card[] hand;
	private GameState gameState;
	Player player;

	/**
	 * Creates a new empty hand.
	 */
	public Hand(GameState gameState, Player player) {
		hand = new Card[4];
		this.gameState = gameState;
		this.player = player;
	}

	public void load(Hand save){
		hand = save.hand;
	}

	/**
	 * Returns a card at a specified position. Null if there's no card at that
	 * position.
	 *
	 * @param i
	 *            coordinate
	 * @return a card at a specified position
	 */
	public Card get(int i) {
		return hand[i];
	}

	/**
	 * Places a card at a specified position.
	 *
	 * @param i
	 *            coordinate
	 * @param card
	 *            a card to be set
	 */
	public void set(int i, Card card) {
		hand[i] = card;
		gameState.gui.getHand(player).getCell(i).drawCard(card);
	}

	/**
	 * Updates a card at a specified position.
	 *
	 * @param i
	 *            coordinate
	 */
	public void update(int i) {
		gameState.gui.getHand(player).getCell(i).drawCard(hand[i]);
	}

	public void update() {
		for (int i = 0; i < 4; ++i)
			gameState.gui.getHand(player).getCell(i).drawCard(hand[i]);
	}

	/**
	 * Removes a card from the hand at a specified position.
	 *
	 * @param i
	 *            coordinate
	 */
	public void remove(int i) {
		hand[i] = null;
		gameState.gui.getHand(player).getCell(i).drawCard(null);
	}

	/**
	 * Tests if there is any card in hand.
	 */
	public boolean isEmpty() {
		for (int i = 0; i < 4; ++i)
			if (hand[i] != null)
				return false;
		return true;
	}

	/**
	 * Tests if there is a card at a specified position.
	 */
	public boolean isEmpty(int i) {
		return hand[i] == null;
	}

}
