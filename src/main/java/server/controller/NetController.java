package server.controller;

import game.controller.AdvancingStage;
import game.controller.DiscardingStage;
import game.controller.DrawingStage;
import game.controller.GameOver;
import game.controller.PlayingStage;
import game.controller.Stage;
import game.model.GameState;
import game.model.Player;
import game.view.EventReceiver.TriggerEvent;
import game.view.EventReceiver.TriggerEventHandler;
import game.view.GUI;
import game.view.GUI.Button;
import game.view.SimpleGUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import javax.swing.JFileChooser;

public class NetController implements TriggerEventHandler {

	public final GameState gameState;
	public final GUI gui;
	private int turn;
	private Client zombiePlayer;
	private Client humanPlayer;

	public NetController(Client zombiePlayer, Client humanPlayer) {
		System.err.println("Creating net controller...");
		this.zombiePlayer = zombiePlayer;
		this.humanPlayer = humanPlayer;
		gui = new SimpleGUI(this);
		// gui = new DummyGUI(this); <- do zmiany jak tylko sie pojawi
		gameState = new GameState(gui);
		gui.addButtonMouseListener(Button.Save, new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				saveState();
			}
		});
		System.err.println("Done");
		//slidera zostawiam bardziej obcykanym w temacie
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

	public void game(Player startingPlayer) {
		turn = gameState.getTurn();
		if (turn == 0) {
			gameState.sendMessage("Zombies are controlled by " + zombiePlayer.getLogin());
			gameState.sendMessage("Humans are controlled by " + humanPlayer.getLogin());
			gameState.sendMessage("Time to begin, good luck!");
		}
		Stage[] stages = new Stage[4];
		stages[0] = new AdvancingStage(gameState, gui);
		stages[1] = new DrawingStage(gameState, gui);
		stages[2] = new DiscardingStage(gameState, gui);
		stages[3] = new PlayingStage(gameState, gui);
		Player[] players = new Player[2];
		players[0] = Player.ZOMBIE;
		players[1] = Player.HUMAN;
		for (Player p : players) {
			gui.setPlayer(p);
			gui.setButtonEnabled(Button.EndTurn, false);
			gui.setButtonEnabled(Button.ApplySelection, false);
			gui.setButtonEnabled(Button.CancelSelection, false);
		}
		System.err.println("Game started");
		if (startingPlayer == Player.HUMAN) {
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

	@Override
	public void receiveTriggerEvent(TriggerEvent e) {
		e.trigger(gameState);
	}
	

}
