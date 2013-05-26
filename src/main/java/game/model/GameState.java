package game.model;

import game.controller.Stage.StageType;
import game.model.Modifier.ModifierType;
import game.view.GUI;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utility.TypedSet;

/**
 * A class representing a gamestate.
 */
public class GameState implements Serializable {
	private static final long serialVersionUID = 6067960933766278369L;
	public transient final GUI gui;
	private TypedSet<Modifier, ModifierType> modifiers = new TypedSet<>();
	private Board board;
	private Deck zombieDeck;
	private Deck humanDeck;
	private Hand zombieHand;
	private Hand humanHand;
	private Random random;
	private List<String> messages;
	private int turn;
	private StageType stage;
	private Player player;
	private PlayerAccount zombiePlayer;
	private PlayerAccount humanPlayer;
	public byte[] lastSave;

	/**
	 * Creates a new gamestate.
	 */
	public GameState(GUI gui) {
		System.err.println("Creating GameState...");
		this.gui = gui;
		board = new Board(this);
		zombieDeck = new Deck(this, Player.ZOMBIE, random);
		humanDeck = new Deck(this, Player.HUMAN, random);
		zombieHand = new Hand(this, Player.ZOMBIE);
		humanHand = new Hand(this, Player.HUMAN);
		messages = new ArrayList<String>();
	}
	
	/**
	 * Creates a new gamestate using specified players' accounts.
	 */
	public GameState(GUI gui,PlayerAccount zombieAccount, PlayerAccount humanAccount){
		this(gui);
		this.zombiePlayer = zombieAccount;
		this.humanPlayer = humanAccount;
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

	public void update() {
		board.update();
		humanHand.update();
		zombieHand.update();
		gui.drawGlobalModifiers(modifiers);
	}

	public TypedSet<Modifier, ModifierType> getModifiers() {
		return modifiers;
	}

	public void nextStage() {
		Modifier.nextStage(modifiers);
		board.nextStage();
	}

	public void sendMessage(String message) {
		messages.add(message);
		gui.modelSendsMessage(message);
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public StageType getStage() {
		return stage;
	}

	public void setStage(StageType stage) {
		this.stage = stage;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public PlayerAccount getZombiePlayerAccount(){
		return zombiePlayer;
	}
	
	public PlayerAccount getHumanPlayerAccount(){
		return humanPlayer;
	}

	public byte[] save() {
		System.err.print("Saving...");
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try {
			ObjectOutputStream out = new ObjectOutputStream(bytes);
			out.writeObject(this);
		} catch (IOException e) {
			System.err.println("\tfailed.");
			e.printStackTrace();
			return null;
		}
		System.err.println("\tdone.");
		return bytes.toByteArray();
	}
	
	private void updateMessages(){
		gui.modelSendsAllMessages(messages);
	}

	public void load(byte[] bytes) {
		System.err.print("Loading...");
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream in = new ObjectInputStream(bin);
			GameState save = (GameState) in.readObject();
			modifiers = save.modifiers;
			board.load(save.board);
			zombieDeck.load(save.zombieDeck);
			humanDeck.load(save.humanDeck);
			zombieHand.load(save.zombieHand);
			humanHand.load(save.humanHand);
			random = save.random;
			messages = save.messages;
			turn = save.turn;
			stage = save.stage;
			player = save.player;
			zombiePlayer = save.zombiePlayer;
			humanPlayer = save.humanPlayer;
			update();
			updateMessages();
			System.err.println("\tdone.");
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("\tfailed.");
			e.printStackTrace();
		}
	}

}
