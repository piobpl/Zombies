package game.controller;

import game.model.Player;

/**
 * A class that handles the end of the game.
 */

public class GameOver extends RuntimeException {

	private static final long serialVersionUID = 5944458670405620920L;

	public final Player won;

	public GameOver(Player won) {
		this.won = won;
	}

}
