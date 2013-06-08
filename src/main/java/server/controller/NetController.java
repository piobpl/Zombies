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
/**
 * 
 * @author jerzozwierz
 *
 */
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
		System.err.println("Done");
	}

	

	public void game() {
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
		try {
			while (true) {
				gameState.setTurn(turn);
				gameState.sendMessage("Round #" + turn);
				for (Player p : players) {
					gui.setPlayer(p);
					gameState.setPlayer(p);
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
			//TODO tutaj wysylamy komunikaty do graczy
			//ewentualnie zmieniajac staty - ale na to pewnie nie bedzie czasu
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
