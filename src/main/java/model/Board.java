package model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import model.cards.helpers.Card;
import model.traps.Trap;

/**
 * A class representing a board.
 */

public class Board {

	private Card[][] board;
	private GameState gameState;
	private List<HashSet<Trap>> traps;

	/**
	 * Creates a new empty board.
	 */
	public Board(GameState gameState) {
		board = new Card[5][3];
		this.gameState = gameState;
		traps = new LinkedList<HashSet<Trap>>();
		for (int i = 0; i < 15; i++)
			traps.add(new HashSet<Trap>());
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
		gameState.gui.getBoard().getCell(x, y).draw(null);
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
		gameState.gui.getBoard().getCell(x, y).draw(card);
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
		gameState.gui.getBoard().getCell(x, y).draw(board[x][y]);
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
	public boolean is(int x, int y, String name) {
		return board[x][y] != null && board[x][y].getName() == name;
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

	public void exchangeContent(int x1, int y1, int x2, int y2) {
		Card tmpCard = get(x1, y1);
		HashSet<Trap> tmpTrapsSet = getTraps(x1, y1);
		remove(x1, y1);
		set(x1, y1, get(x2, y2));
		getTraps(x1, y1).clear();
		getTraps(x1, y1).addAll(getTraps(x2, y2));
		remove(x2, y2);
		set(x2, y2, tmpCard);
		getTraps(x2, y2).clear();
		getTraps(x2, y2).addAll(tmpTrapsSet);
		update(x1, y1);
		update(x2, y2);
	}

	public HashSet<Trap> getTraps(int x, int y) {
		return traps.get(x * 3 + y);
	}

	public void nextPhase() {
		for(int i = 0; i < 5; ++i)
			for(int j = 0; j < 3; ++j)
				board[i][j].modifiers.nextPhase();
	}

}
