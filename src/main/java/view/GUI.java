package view;

import model.GameState;
import controller.Controller;

public class GUI {
	public final Controller controller;
	public final GameState gameState;
	public final GUIController guiController;

	public GUI(Controller controller, GameState gameState) {
		System.err.println("Creating GUI...");
		this.controller = controller;
		this.gameState = gameState;
		guiController = new GUIController(this);
	}
	// TODO
}
