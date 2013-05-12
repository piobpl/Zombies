package controller;

import model.GameState;
import model.Player;
import view.GUI;

public class Controller {
	public final GameState gameState;
	public final GUI gui;
	public final Selector selector;

	public Controller() {
		System.err.println("Creating Controller...");
		gameState = new GameState();
		gui = gameState.gui;
		selector = new Selector(gui.eventReceiver, gameState);
		System.err.println("Done");
	}

	@SuppressWarnings("unused")
	private Player turn = Player.ZOMBIE;

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
