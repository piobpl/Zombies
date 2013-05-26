package game.controller;

import game.model.GameState;
import game.model.Player;

public class GameOver extends RuntimeException {

	private static final long serialVersionUID = 5944458670405620920L;

	public final Player won;
	public final GameState gameState;

	public GameOver(Player won, GameState gameState) {
		this.won = won;
		this.gameState = gameState;
		gameState.getHumanPlayerAccount().incrementGamesPlayed();
		gameState.getHumanPlayerAccount().incrementGamesPlayed();
		if (won.equals(Player.ZOMBIE)) {
			gameState.getZombiePlayerAccount().incrementGamesWon();
		} else {
			gameState.getHumanPlayerAccount().incrementGamesWon();
		}
	}

}
