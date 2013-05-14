package cards.helpers;

import model.Card;
import model.GameState;
import model.Player;
import controller.GameOver;

/**
 * Klasa wspomagająca ruszanie zombich i psów po planszy.
 *
 * @author michal
 *
 */

// TODO : sprawdzanie czy pole puste, nie tylko isEmpty
public abstract class Mover {
	public static boolean moveForward(GameState gameState, int x, int y) {
		Card card = gameState.getBoard().get(x, y);
		if (x == 4 && card.getName().equals("Zombie"))
			throw new GameOver(Player.ZOMBIE);
		if (!gameState.getBoard().isEmpty(x + 1, y))
			return false;
		gameState.getBoard().set(x + 1, y, card);
		gameState.getBoard().set(x, y, null);
		return true;
	}

	public static boolean moveBackward(GameState gameState, int x, int y) {
		if (x == 0 || !gameState.getBoard().isEmpty(x - 1, y))
			return false;
		Card card = gameState.getBoard().get(x, y);
		gameState.getBoard().set(x - 1, y, card);
		gameState.getBoard().set(x, y, null);
		return true;
	}

	public static boolean moveLeft(GameState gameState, int x, int y) {
		if (y == 0 || !gameState.getBoard().isEmpty(x, y - 1))
			return false;
		Card card = gameState.getBoard().get(x, y);
		gameState.getBoard().set(x, y - 1, card);
		gameState.getBoard().set(x, y, null);
		return true;
	}

	public static boolean moveRight(GameState gameState, int x, int y) {
		if (y == 2 || !gameState.getBoard().isEmpty(x, y + 1))
			return false;
		Card card = gameState.getBoard().get(x, y);
		gameState.getBoard().set(x, y + 1, card);
		gameState.getBoard().set(x, y, null);
		return true;
	}
}
