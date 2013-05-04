package view;

import model.GameState;
import controller.Controller;

public class GUI {
	public final Controller controller;
	public final GameState gameState;

	public GUI(Controller controller, GameState gameState) {
		System.err.println("Creating GUI...");
		this.controller = controller;
		this.gameState = gameState;
	}

	// TODO

	public EventReceiver getEventReceiver() {
		return null;
	}

	public void removeEventReceiver() {
	}

}
