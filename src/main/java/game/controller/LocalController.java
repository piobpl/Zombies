package game.controller;

import game.model.GameState;
import game.model.Player;
import game.view.GUI;
import game.view.GUI.Button;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import javax.swing.JFileChooser;

public class LocalController {

	public final GameState gameState;
	public final GUI gui;
	private int turn;

	public LocalController() {
		System.err.println("Creating Controller...");
		gui = new GUI();
		gameState = new GameState(gui);
		gui.saveButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				saveState();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
		System.err.println("Done");
	}

	public void saveState() {
		byte[] data = gameState.getLastSave();
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				Files.write(fileChooser.getSelectedFile().toPath(), data,
						StandardOpenOption.CREATE);
			} catch (IOException e) {
				System.err.println("Error during saving process!");
				e.printStackTrace();
			}
		}
	}

	public void game() {
		turn = 0;
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
				gameState.setTurn(turn);
				gameState.sendMessage("Round #" + turn);
				for (Player p : players) {
					gameState.setPlayer(p);
					gameState.setLastSave(gameState.save());
					gameState.sendMessage(p + "'s turn.");
					for (Stage s : stages) {
						gameState.setStage(s.getStageType());
						gameState.nextStage();
						s.perform(p);
					}
				}
				++turn;
			}
		} catch (GameOver gameOver) {
			gameState.sendMessage(gameOver.won + " has won!");
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gui.exit();
	}

	public void gameLoader() {
		turn = gameState.getTurn();
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
		System.err.println("Game loaded and started");
		try {
			if (gameState.getPlayer() == Player.HUMAN) {
				gameState.setTurn(turn);
				gameState.sendMessage("Round #" + turn);
				gameState.setLastSave(gameState.save());
				gameState.sendMessage(gameState.getPlayer() + "'s turn.");
				for (Stage s : stages) {
					gameState.setStage(s.getStageType());
					gameState.nextStage();
					s.perform(gameState.getPlayer());
				}
				++turn;
			}
		} catch (GameOver gameOver) {
			gameState.sendMessage(gameOver.won + " has won!");
		}
		try {
			while (true) {
				gameState.setTurn(turn);
				gameState.sendMessage("Round #" + turn);
				for (Player p : players) {
					gameState.setPlayer(p);
					gameState.setLastSave(gameState.save());
					gameState.sendMessage(p + "'s turn.");
					for (Stage s : stages) {
						gameState.setStage(s.getStageType());
						gameState.nextStage();
						s.perform(p);
					}
				}
				++turn;
			}
		} catch (GameOver gameOver) {
			gameState.sendMessage(gameOver.won + " has won!");
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gui.exit();
	}

	public static void main(String args[]) {
		new LocalController().game();
	}

}
