package model;

import java.util.LinkedList;
import java.util.List;

import model.Card.CardType;
import model.Trap.TrapType;
import utility.TypedSet;

/**
 * A class representing a board.
 */

public class Board {

	private Card[][] board;
	private GameState gameState;
	private List<TypedSet<Trap, TrapType>> traps;

	/**
	 * Creates a new empty board.
	 */
	public Board(GameState gameState) {
		board = new Card[5][3];
		this.gameState = gameState;
		traps = new LinkedList<>();
		for (int i = 0; i < 15; i++)
			traps.add(new TypedSet<Trap, TrapType>());
	}

	/**
	 * Removes a card from the board at a specified position.
	 *
	 * @param x
	 *            first coordinate of the card
	 * @param y
	 *            second coordinate of the card
	 */
	public void remove(int x, int y) {
		board[x][y] = null;
		gameState.gui.getBoard().getCell(x, y).drawCard(null);
	}

	/**
	 * Places a card at a specified position.
	 *
	 * @param x
	 *            first coordinate
	 * @param y
	 *            second coordinate
	 * @param card
	 *            a card to be set
	 */
	public void set(int x, int y, Card card) {
		board[x][y] = card;
		gameState.gui.getBoard().getCell(x, y).drawCard(card);
	}

	/**
	 * Updates a card at a specified position.
	 *
	 * @param x
	 *            first coordinate
	 * @param y
	 *            second coordinate
	 */
	public void update(int x, int y) {
		gameState.gui.getBoard().getCell(x, y).drawCard(board[x][y]);
	}

	public void update() {
		for (int x = 0; x < 5; ++x)
			for (int y = 0; y < 3; ++y)
				update(x, y);
	}

	/**
	 * Returns a card at a specified position. Null if there's no card at that
	 * position.
	 *
	 * @param x
	 *            first coordinate
	 * @param y
	 *            second coordinate
	 * @return a card at a specified position
	 */
	public Card get(int x, int y) {
		return board[x][y];
	}

	/**
	 * Checks whether a specified position on the board is empty.
	 *
	 * @param x
	 *            first coordinate
	 * @param y
	 *            second coordinate
	 * @return true if the specified position on the board is empty
	 */
	public boolean isEmpty(int x, int y) {
		return board[x][y] == null;
	}

	/**
	 * Checks whether a specified position on the board is empty and doesn't
	 * contain any traps.
	 *
	 * @param x
	 *            first coordinate
	 * @param y
	 *            second coordinate
	 * @return true if the specified position on the board is empty and doesn't
	 *         contain any traps.
	 */
	public boolean isCompletelyEmpty(int x, int y) {
		return (board[x][y] == null && getTraps(x, y).isEmpty());
	}

	/**
	 * Checks whether a specified position has card with specified name.
	 *
	 * @param x
	 *            first coordinate
	 * @param y
	 *            second coordinate
	 * @return true if the specified position on the board is empty
	 */
	public boolean is(int x, int y, CardType type) {
		return board[x][y] != null && board[x][y].getType() == type;
	}

	/**
	 * Exchange cards at a specified position (including traps);
	 *
	 * @param x1
	 *            first coordinate of the first card
	 * @param y1
	 *            second coordinate of the first card
	 * @param x2
	 *            first coordinate of the second card
	 * @param y2
	 *            second coordinate of the second card
	 */

	// TODO ustawienie handlerow zmiany dla gui
	public void exchangeContent(int x1, int y1, int x2, int y2) {
		Card tmpCard = get(x1, y1);
		set(x1, y1, get(x2, y2));
		set(x2, y2, tmpCard);
		update(x1, y1);
		update(x2, y2);
		TypedSet<Trap, TrapType> tmpSet = traps.get(x1 * 3 + y1);
		traps.set(x1 * 3 + y1, traps.get(x2 * 3 + y2));
		traps.set(x2 * 3 + y2, tmpSet);
	}

	public TypedSet<Trap, TrapType> getTraps(int x, int y) {
		return traps.get(x * 3 + y);
	}

	public void nextStage() {
		for (int i = 0; i < 5; ++i)
			for (int j = 0; j < 3; ++j)
				if (board[i][j] != null)
					Modifier.nextStage(board[i][j].getModifiers());
	}

}
