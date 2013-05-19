package model.cards.helpers;

import model.GameState;
import model.Player;
import model.modifiers.ModifierType;
import model.traps.Trap;
import utility.Pair;
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
		return gameState.globalModifiers.contains(ModifierType.BEENFROZEN);
	}

	public static boolean isMovePossible(GameState gameState, Pair<Integer, Integer> from, Pair<Integer, Integer> to, Card card){
		if(isFrozen(gameState) || !gameState.getBoard().isEmpty(to.first, to.second)){
			return false;
		}
		if(card==null)
			card=gameState.getBoard().get(from.first, from.second);
		for(Trap t:gameState.getBoard().getTraps(to.first, to.second)){
			System.err.println(t.isMovePossible(card, from));
			if(!t.isMovePossible(card, from)){
				return false;
			}
		}
		return true;
	}
	
	public static boolean moveTo(GameState gameState, Pair<Integer, Integer> from, Pair<Integer, Integer> to){
		Card card=gameState.getBoard().get(from.first, from.second);
		if(!isMovePossible(gameState, from, to, null))
			return false;
		gameState.getBoard().set(to.first, to.second, card);
		gameState.getBoard().set(from.first, from.second, null);
		for(Trap t:gameState.getBoard().getTraps(to.first, to.second)){
			t.movedOn(card);
		}
		return true;
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
		/*gameState.getBoard().set(x + 1, y, card);
		gameState.getBoard().set(x, y, null);*/
		moveTo(gameState, new Pair<Integer, Integer>(x, y), new Pair<Integer, Integer>(x+1, y));
		return true;
	}

	public static boolean moveBackward(GameState gameState, int x, int y) {
		if(isFrozen(gameState)){
			return false;
		}
		if (x == 0 || !gameState.getBoard().isEmpty(x - 1, y))
			return false;
		/*Card card = gameState.getBoard().get(x, y);
		gameState.getBoard().set(x - 1, y, card);
		gameState.getBoard().set(x, y, null);*/
		moveTo(gameState, new Pair<Integer, Integer>(x, y), new Pair<Integer, Integer>(x-1, y));
		return true;
	}

	public static boolean moveLeft(GameState gameState, int x, int y) {
		if(isFrozen(gameState)){
			return false;
		}
		if (y == 0 || !gameState.getBoard().isEmpty(x, y - 1))
			return false;
		/*Card card = gameState.getBoard().get(x, y);
		gameState.getBoard().set(x, y - 1, card);
		gameState.getBoard().set(x, y, null);*/
		moveTo(gameState, new Pair<Integer, Integer>(x, y), new Pair<Integer, Integer>(x, y-1));
		return true;
	}

	public static boolean moveRight(GameState gameState, int x, int y) {
		if(isFrozen(gameState)){
			return false;
		}
		if (y == 2 || !gameState.getBoard().isEmpty(x, y + 1))
			return false;
		/*Card card = gameState.getBoard().get(x, y);
		gameState.getBoard().set(x, y + 1, card);
		gameState.getBoard().set(x, y, null);*/
		moveTo(gameState, new Pair<Integer, Integer>(x, y), new Pair<Integer, Integer>(x, y+1));
		return true;
	}
}
