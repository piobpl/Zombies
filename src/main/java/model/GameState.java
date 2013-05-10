package model;

import view.GUI;

public class GameState {
	public final GUI gui;
	private Board board;
	private Deck zombieDeck;
	private Deck humanDeck;
	private Hand zombieHand;
	private Hand humanHand;

	public Board getBoard() {
		return board;
	}

	public Deck getDeck(Player player) {
		if (player == Player.ZOMBIE) {
			return zombieDeck;
		} else {
			return humanDeck;
		}
	}

	public Hand getHand(Player player) {
		if (player == Player.ZOMBIE) {
			return zombieHand;
		} else {
			return humanHand;
		}
	}

	public GameState() {
		System.err.println("Creating GameState...");
		gui = new GUI();
		board = new Board();
		zombieDeck = new Deck(Player.ZOMBIE);
		humanDeck = new Deck(Player.HUMAN);
		zombieHand = new Hand(this, Player.ZOMBIE);
		humanHand = new Hand(this, Player.HUMAN);
	}
	
	//TODO czy gamestate ma jeszcze cos robic?
}
