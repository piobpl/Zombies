package controller;

import model.GameState;
import view.GUI;

public class Controller {
	public final GameState gameState;
	public final GUI gui;

	public Controller() {
		System.err.println("Creating Controller...");
		gameState = new GameState(this);
		gui = gameState.gui;
		System.err.println("DONE");
	}

	// TODO

	private void advancingStage() {
	}

	private void drawingStage() {
	}

	private void discardingStage() {
	}

	private void playingStage() {
	}

	public void game() {
		System.err.println("Game started");
		while (true) {
			advancingStage();
			drawingStage();
			discardingStage();
			playingStage();
		}
	}

	public static void main(String args[]) {
		new Controller().game();
	}
}
