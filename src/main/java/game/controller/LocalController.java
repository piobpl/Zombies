package game.controller;

import game.model.GameState;
import game.model.Player;
import game.view.GUI;
import game.view.GUI.Button;

public class LocalController {

	public final GameState gameState;
	public final GUI gui;
	private int stage;

	public LocalController() {
		System.err.println("Creating Controller...");
		gui = new GUI();
		gameState = new GameState(gui);
		System.err.println("Done");
	}

	public void game() {
		stage = 0;
		Stage[] stages = new Stage[4];
		stages[0] = new AdvancingStage(gameState, gui);
		stages[1] = new DrawingStage(gameState, gui);
		stages[2] = new DiscardingStage(gameState, gui);
		stages[3] = new PlayingStage(gameState, gui);
		Player[] players = new Player[2];
		players[0] = Player.ZOMBIE;
		players[1] = Player.HUMAN;
		gui.setButtonEnabled(Button.EndTurn, false);
		gui.setButtonEnabled(Button.ApplySelection, false);
		gui.setButtonEnabled(Button.CancelSelection, false);
		System.err.println("Game started");
		try {
			while (true) {
				gui.sendMessage("Round #" + stage);
				for (Player p : players) {
					gui.sendMessage(p + "'s turn.");
					for (Stage s : stages) {
						gameState.nextStage();
						s.perform(p);
					}
				}
				++stage;
			}
		} catch (GameOver gameOver) {
			gui.sendMessage(gameOver.won + " has won!");
		}
		gui.exit();
	}

	public static void main(String args[]) {
		new LocalController().game();
	}
}
