package game.model;

import game.model.Card.CardType;
import game.model.Trap.TrapType;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import utility.Pair;
import utility.TypedSet;

/**
 * A class representing a board.
 */

public class Board implements Serializable {

	private static final long serialVersionUID = 7656346014331677774L;
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

	public void load(Board save){
		board = save.board;
		traps = save.traps;
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
		gameState.gui.getBoard().getCell(x, y).drawTraps(traps.get(x * 3 + y));
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
	 * Exchange cards at a specified position (including traps); Returns true if
	 * the cards were exchanged.
	 *
	 * @param x1
	 *            first coordinate of the first card
	 * @param y1
	 *            second coordinate of the first card
	 * @param x2
	 *            first coordinate of the second card
	 * @param y2
	 *            second coordinate of the second card
	 * @return true if the cards were exchanged
	 */

	// poprawilem - troche sie skomplikowalo, a wszystko po to zeby uzywac
	// Movera.
	// w poprzedniej wersji po zamianie nie byly odpalane np. efekty trapow
	// (przyklad: napalm)
	public boolean exchangeContent(int x1, int y1, int x2, int y2) {
		Pair<Integer, Integer> p1 = new Pair<>(x1, y1);
		Pair<Integer, Integer> p2 = new Pair<>(x2, y2);
		Card tmpCard1 = get(x1, y1);
		Card tmpCard2 = get(x2, y2);
		board[x1][y1] = null;
		board[x2][y2] = null;
		if (!MoveMaker.isMovePossible(gameState, p1, p2, tmpCard1)) {
			board[x1][y1] = tmpCard1;
			board[x2][y2] = tmpCard2;
			return false;
		}
		if (!MoveMaker.isMovePossible(gameState, p2, p1, tmpCard2)) {
			board[x1][y1] = tmpCard1;
			board[x2][y2] = tmpCard2;
			return false;
		}
		board[x1][y1] = tmpCard1;
		MoveMaker.moveTo(gameState, p1, p2);
		board[x2][y2] = tmpCard2;
		MoveMaker.moveTo(gameState, p2, p1);
		board[x2][y2] = tmpCard1;
		update(x1, y1);
		update(x2, y2);
		return true;
		/*
		 * TypedSet<Trap, TrapType> tmpSet = traps.get(x1 * 3 + y1);
		 * traps.set(x1 * 3 + y1, traps.get(x2 * 3 + y2)); traps.set(x2 * 3 +
		 * y2, tmpSet);
		 */
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
