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

}
