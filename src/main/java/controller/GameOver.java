package controller;

import model.Player;

public class GameOver extends RuntimeException {

	private static final long serialVersionUID = 5944458670405620920L;

	public final Player won;
	
	public GameOver(Player won){
		this.won = won;
	}
	
}
