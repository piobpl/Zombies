package game.model;

import game.controller.GameOver;
import game.model.Card.CardType;
import game.model.Modifier.ModifierType;
import utility.Pair;

/**
 * Klasa wspomagająca ruszanie zombich i psów po planszy.
 *
 * @author michal, Edoipi
 *
 */

public abstract class MoveMaker {

	public static boolean isFrozen(GameState gameState) {
		return gameState.getModifiers().contains(ModifierType.FROZEN);
	}

	public static boolean isFrozen(Card card) {
		return card.getModifiers().contains(ModifierType.FROZEN);
	}

	/**
	 *
	 * Przy założeniu że na obu polach stoją zombiaki, funkcja sprawdza czy mogą
	 * się połączyć (potrzebne w kartach Mass, Change)
	 */
	public static boolean isMergePossible(GameState gameState,
			Pair<Integer, Integer> from, Pair<Integer, Integer> to, Card card) {
		if (isFrozen(gameState))
			return false;
		if (card == null)
			card = gameState.getBoard().get(from.first, from.second);
		if (card == null)
			return false;
		if (isFrozen(card))
			return false;
		for (Trap t : gameState.getBoard().getTraps(to.first, to.second).asList()) {
			if (!t.isMovePossible(card, from)) {
				System.err.println("Trap " + t
						+ "says you can't go here if you are " + card);
				return false;
			}
		}
		return true;
	}

	public static boolean isMovePossible(GameState gameState,
			Pair<Integer, Integer> from, Pair<Integer, Integer> to, Card card) {
		if (!gameState.getBoard().isEmpty(to.first, to.second))
			return false;
		return isMergePossible(gameState, from, to, card);
	}

	public static boolean moveTo(GameState gameState,
			Pair<Integer, Integer> from, Pair<Integer, Integer> to,
			boolean hollow) {
		Card card = gameState.getBoard().get(from.first, from.second);
		gameState.getBoard().set(to.first, to.second, card);
		gameState.getBoard().set(from.first, from.second, null);
		if (!hollow)
			for (Trap t : gameState.getBoard().getTraps(to.first, to.second).asList()) {
				t.movedOn(card);
			}
		gameState.update();
		return true;
	}

	public static boolean moveForward(GameState gameState, int x, int y,
			boolean hollow) {
		Card current = gameState.getBoard().get(x, y);
		if (x == 4 && current != null && current.getType() == CardType.ZOMBIE)
			throw new GameOver(Player.ZOMBIE);
		if (!isMovePossible(gameState, new Pair<Integer, Integer>(x, y),
				new Pair<Integer, Integer>(x + 1, y), null))
			return false;
		Card next = gameState.getBoard().get(x + 1, y);
		if (current.getType() == CardType.ZOMBIE) {
			if (next != null && next.getType() == CardType.BARREL) {
				if (current.getModifiers().contains(ModifierType.HUMAN))
					current.getModifiers().remove(ModifierType.HUMAN);
				else
					gameState.getBoard().set(x, y, null);
				gameState.getBoard().set(x + 1, y, null);
				return true;
			}
		}
		if (current.getType() == CardType.DOGS) {
			if (next != null && next.getType() == CardType.BARREL) {
				gameState.getBoard().set(x, y, null);
				gameState.getBoard().set(x + 1, y, null);
				return true;
			}
		}
		if (next != null)
			return false;
		moveTo(gameState, new Pair<Integer, Integer>(x, y),
				new Pair<Integer, Integer>(x + 1, y), hollow);
		return true;
	}

	public static boolean moveBackward(GameState gameState, int x, int y) {
		if (x == 0)
			return false;
		if (!isMovePossible(gameState, new Pair<Integer, Integer>(x, y),
				new Pair<Integer, Integer>(x - 1, y), null))
			return false;
		Card current = gameState.getBoard().get(x, y);
		Card next = gameState.getBoard().get(x - 1, y);
		if (current.getType() == CardType.BARREL && next != null) {
			if (next.getType() == CardType.ZOMBIE) {
				if (next.getModifiers().contains(ModifierType.HUMAN))
					next.getModifiers().remove(ModifierType.HUMAN);
				else
					gameState.getBoard().set(x - 1, y, null);
				gameState.getBoard().set(x, y, null);
				return true;
			}
			if (next.getType() == CardType.DOGS) {
				gameState.getBoard().set(x - 1, y, null);
				return true;
			}
		}
		if (next != null)
			return false;
		moveTo(gameState, new Pair<Integer, Integer>(x, y),
				new Pair<Integer, Integer>(x - 1, y), false);
		return true;
	}

	public static boolean moveLeft(GameState gameState, int x, int y) {
		if (!isMovePossible(gameState, new Pair<Integer, Integer>(x, y),
				new Pair<Integer, Integer>(x, y - 1), null))
			return false;
		if (y == 0 || !gameState.getBoard().isEmpty(x, y - 1))
			return false;
		moveTo(gameState, new Pair<Integer, Integer>(x, y),
				new Pair<Integer, Integer>(x, y - 1), false);
		return true;
	}

	public static boolean moveRight(GameState gameState, int x, int y) {
		if (!isMovePossible(gameState, new Pair<Integer, Integer>(x, y),
				new Pair<Integer, Integer>(x, y + 1), null))
			return false;
		if (y == 2 || !gameState.getBoard().isEmpty(x, y + 1))
			return false;
		moveTo(gameState, new Pair<Integer, Integer>(x, y),
				new Pair<Integer, Integer>(x, y + 1), false);
		return true;
	}
}
