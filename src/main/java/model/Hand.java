package model;

/**
 * A class representing a hand.
 */
public class Hand {

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
		gameState.gui.getHand(player).getCell(i).draw(card);
	}

	/**
	 * Removes a card from the hand at a specified position.
	 * 
	 * @param i
	 *            coordinate
	 */
	public void remove(int i) {
		hand[i] = null;
	}

}
