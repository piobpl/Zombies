package cards.helpers;

import java.util.Iterator;

import model.Card;
import model.GameState;
import model.Modifier;
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
	
	public static boolean isFrozen(GameState gameState){
		boolean a = false;
		Iterator<Modifier> it = gameState.globalModifiers.iterator();
		while (it.hasNext()) {
			if (it.next().getName().equals("BeenFrozen")) {
				a = true;
			}
		}
		return a;
	}
	
	public static boolean moveForward(GameState gameState, int x, int y) {
		if(isFrozen(gameState)){
			return false;
		}
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
		if(isFrozen(gameState)){
			return false;
		}
		if (x == 0 || !gameState.getBoard().isEmpty(x - 1, y))
			return false;
		Card card = gameState.getBoard().get(x, y);
		gameState.getBoard().set(x - 1, y, card);
		gameState.getBoard().set(x, y, null);
		return true;
	}

	public static boolean moveLeft(GameState gameState, int x, int y) {
		if(isFrozen(gameState)){
			return false;
		}
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
