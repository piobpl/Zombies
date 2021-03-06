package model;

import game.model.Board;
import game.model.Deck;
import game.model.GameState;
import game.model.Hand;
import game.model.Player;

import java.util.Random;

public class GUILessGameState extends GameState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2890373656735118359L;

	public GUILessGameState() {
		super(null);
		board = new GUILessBoard(this);
		random=new Random();
		zombieDeck = new Deck(this, Player.ZOMBIE,random);
		humanDeck = new Deck(this, Player.HUMAN,random);
		zombieHand = new Hand(this, Player.ZOMBIE);
		humanHand = new Hand(this, Player.HUMAN);
	}

	private Board board;
	private Deck zombieDeck;
	private Deck humanDeck;
	private Hand zombieHand;
	private Hand humanHand;
	private Random random;

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public Deck getDeck(Player player) {
		if (player == Player.ZOMBIE) {
			return zombieDeck;
		} else {
			return humanDeck;
		}
	}

	@Override
	public Hand getHand(Player player) {
		if (player == Player.ZOMBIE) {
			return zombieHand;
		} else {
			return humanHand;
		}
	}
}
