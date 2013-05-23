package game.controller;

import game.model.Board;
import game.model.Card.CardType;
import game.model.GameState;
import game.model.MoveMaker;
import game.model.Player;
import game.model.Trap;
import game.model.Trap.TrapType;
import game.view.GUI;
import utility.TypedSet;

public class AdvancingStage implements Stage {
	public final GameState gameState;
	public final GUI gui;

	public AdvancingStage(GameState gameState, GUI gui) {
		this.gameState = gameState;
		this.gui = gui;
	}

	public void perform(Player player) {
		Board board = gameState.getBoard();
		switch (player) {
		case ZOMBIE:
			for (int x = 4; x >= 0; --x)
				for (int y = 0; y < 3; ++y)
					if (board.is(x, y, CardType.ZOMBIE))
						MoveMaker.moveForward(gameState, x, y);
			break;
		case HUMAN:
			for (int x = 0; x < 5; ++x)
				for (int y = 0; y < 3; ++y)
					if (board.is(x, y, CardType.BARREL)) {
						if (x == 0) {
							board.set(x, y, null);
						} else {
							TypedSet<Trap, TrapType> traps = board.getTraps(
									x - 1, y);
							if (traps.containsAny(TrapType.WALL, TrapType.CAR)) {
								board.set(x, y, null);
							} else if (traps.contains(TrapType.PIT)) {
								traps.remove(TrapType.PIT);
								board.set(x, y, null);
							} else {
								MoveMaker.moveBackward(gameState, x, y);
							}
						}
					}
			break;
		}
		gameState.update();
	}
}