package lobby.controller;

import lobby.view.Lobby;

/**
 * A class that takes control over lobby.
 * 
 * @author krozycki
 * 
 */
public class LobbyController {

	public final Lobby lobby;

	/**
	 * Creates LobbyController.
	 */
	public LobbyController() {
		System.err.println("Creating LobbyController...");
		lobby = new Lobby();
		System.err.println("Done");
	}

	/**
	 * Starts new lobby.
	 */
	public static void main(String args[]) {
		new LobbyController();
	}

}
