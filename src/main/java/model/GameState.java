package model;

import modifiers.ModifierSet;
import view.GUI;

/**
 * A class representing a gamestate.
 */
public class GameState {
	public final GUI gui;
	public final ModifierSet globalModifiers = new ModifierSet();
	private Board board;
	private Deck zombieDeck;
	private Deck humanDeck;
	private Hand zombieHand;
	private Hand humanHand;

	/**
	 * Creates a new gamestate.
	 */
	public GameState(GUI gui) {
		System.err.println("Creating GameState...");
		this.gui = gui;
		board = new Board(this);
		zombieDeck = new Deck(this, Player.ZOMBIE);
		humanDeck = new Deck(this, Player.HUMAN);
		zombieHand = new Hand(this, Player.ZOMBIE);
		humanHand = new Hand(this, Player.HUMAN);
	}

	/**
	 * Returns the board.
	 *
	 * @return a board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Returns the player's deck.
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
	 * Returns the player's hand.
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

}
