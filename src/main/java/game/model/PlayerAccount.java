package game.model;

import java.io.Serializable;

/**
 * A class representing a player's account and storing his achievements.
 * 
 * @author krozycki
 * 
 */
public class PlayerAccount implements Serializable {

	private static final long serialVersionUID = 3980442999032025144L;
	private String name;
	private int gamesPlayed;
	private int gamesWon;

	/**
	 * Creates a new account for the specified name.
	 * 
	 * @param name
	 *            name of the account
	 */
	public PlayerAccount(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the account.
	 * 
	 * @return name of the account
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the number of games played by the player.
	 * 
	 * @return number of played games
	 */
	public int getGamesPlayed() {
		return gamesPlayed;
	}

	/**
	 * Returns the number of games lost by the player.
	 * 
	 * @return number of lost games
	 */
	public int getGamesLost() {
		return gamesPlayed - gamesWon;
	}

	/**
	 * Returns the number of games won by the player.
	 * 
	 * @return number of won games
	 */
	public int getGamesWon() {
		return gamesWon;
	}
	
	/**
	 * Increments the number of games played by the player.
	 */
	public void incrementGamesPlayed(){
		gamesPlayed++;
	}
	
	/**
	 * Increments the number of games won by the player.
	 */
	public void incrementGamesWon(){
		gamesWon++;
	}

}
