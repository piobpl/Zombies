package model;

/**
 * A class representing a board.
 */

public class Board {

	private Card[][] board;
	private GameState gameState;

	/**
	 * Creates a new empty board.
	 */
	public Board(GameState gameState) {
		board = new Card[5][3];
		this.gameState = gameState;
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

}
