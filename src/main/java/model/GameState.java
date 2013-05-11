package model;

import view.GUI;

/**
 * A class representing a gamestate.
 */
public class GameState {
	public final GUI gui;
	private Board board;
	private Deck zombieDeck;
	private Deck humanDeck;
	private Hand zombieHand;
	private Hand humanHand;

	/**
	 * Returns the board.
	 * 
	 * @return a board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Returns the a player's deck.
	 * 
	 * @param player
	 *            a player whose deck is to be returned
	 * 
	 * @return a player's deck
	 */
	public Deck getDeck(Player player) {
		if (player == Player.ZOMBIE) {
			return zombieDeck;
		} else {
			return humanDeck;
		}
	}

	/**
	 * Returns the a player's hand.
	 * 
	 * @param player
	 *            a player whose hand is to be returned
	 * 
	 * @return a player's hand
	 */
	public Hand getHand(Player player) {
		if (player == Player.ZOMBIE) {
			return zombieHand;
		} else {
			return humanHand;
		}
	}

	/**
	 * Creates a new gamestate.
	 */
	public GameState() {
		System.err.println("Creating GameState...");
		gui = new GUI();
		board = new Board();
		zombieDeck = new Deck(Player.ZOMBIE);
		humanDeck = new Deck(Player.HUMAN);
		zombieHand = new Hand();
		humanHand = new Hand();
	}

}
