package model;

import view.GUI;
import controller.Controller;

public class GameState {
	public final Controller controller;
	public final GUI gui;

	public GameState(Controller controller) {
		System.err.println("Creating GameState...");
		this.controller = controller;
		gui = new GUI(controller, this);
	}
	// TODO
}
