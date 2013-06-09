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

import utility.Compressor;
import utility.TypedSet;

/**
 * A class representing a gamestate.
 */
public class GameState implements Serializable {
	private static final long serialVersionUID = 6067960933766278369L;
	public transient GUI gui;
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
	private transient List<byte[]> saveList;

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
		saveList = new ArrayList<byte[]>();
	}

	/**
	 * Creates a new gamestate using specified players' accounts.
	 */
	public GameState(GUI gui, PlayerAccount zombieAccount,
			PlayerAccount humanAccount) {
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

	/**
	 * Return the zombie player account.
	 *
	 * @return zombie player account
	 */
	public PlayerAccount getZombiePlayerAccount() {
		return zombiePlayer;
	}

	/**
	 * Returns the human player account.
	 *
	 * @return human player account
	 */
	public PlayerAccount getHumanPlayerAccount() {
		return humanPlayer;
	}

	/**
	 * Saves the current gamestate to byte array.
	 *
	 * @return byte array representing the saved gamestate
	 * @throws IOException
	 */
	public byte[] save() throws IOException {
		System.err.print("Saving...");
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bytes);
		out.writeObject(this);
		out.flush();
		out.close();
		System.err.println("\tdone.");
		return Compressor.compress(bytes.toByteArray());
	}

	/**
	 * Sets the messages area in the gui according to the current state of the
	 * gamestate's messages list.
	 */
	public void updateMessages() {
		gui.modelSetsAllMessages(messages);
	}

	/**
	 * Loads the byte array representing the saved gamestate, to the gamestate.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public GameState load(byte[] bytes) throws IOException, ClassNotFoundException {
		System.err.print("Loading...");
		ByteArrayInputStream bin = new ByteArrayInputStream(
				Compressor.decompress(bytes));
		ObjectInputStream in = new ObjectInputStream(bin);
		GameState save = (GameState) in.readObject();
		save.gui = gui;
		System.err.println("\tdone.");
		return save;
	}

	public byte[] getLastSave() {
		return saveList.get(saveList.size() - 1);
	}

	public byte[] getSave(int i) {
		return saveList.get(i);
	}

	public void setLastSave(byte[] lastSave) {
		saveList.add(lastSave);
		while (saveList.size() > 5)
			saveList.remove(0);
		gui.drawHistorySlider(saveList.size());
	}

	public void setGUI(GUI gui) {
		this.gui = gui;
	}
}
