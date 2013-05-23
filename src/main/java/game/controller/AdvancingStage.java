package game.controller;

import game.model.Board;
import game.model.Card.CardType;
import game.model.GameState;
import game.model.MoveMaker;
import game.model.Player;
import game.view.GUI;

public class AdvancingStage implements Stage {
	public final GameState gameState;
	public final GUI gui;

	public AdvancingStage(GameState gameState, GUI gui) {
		this.gameState = gameState;
		this.gui = gui;
	}

	public void perform(Player player) {
		switch (player) {
		case ZOMBIE:
			Board board = gameState.getBoard();
			for (int x = 4; x >= 0; --x)
				for (int y = 0; y < 3; ++y)
					if (board.is(x, y, CardType.ZOMBIE))
						MoveMaker.moveForward(gameState, x, y);
			break;
		case HUMAN:
			break;
		}
		gameState.update();
	}
}