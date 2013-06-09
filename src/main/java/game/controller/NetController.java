package game.controller;

import game.model.GameState;
import game.model.Player;
import game.view.DummyGUI;
import game.view.EventReceiver.TriggerEvent;
import game.view.EventReceiver.TriggerEventHandler;
import game.view.GUI.Button;
import utility.Listener;

/**
 *
 * @author jerzozwierz
 *
 */
public class NetController implements TriggerEventHandler, Runnable {

	public GameState gameState;
	public DummyGUI gui;
	private int turn;
	Listener zombiePlayer, humanPlayer;

	public NetController(Listener zombiePlayer, Listener humanPlayer) {
		System.err.println("Creating net controller... " + zombiePlayer + " "
				+ humanPlayer);
		this.zombiePlayer = zombiePlayer;
		this.humanPlayer = humanPlayer;
		gui = new DummyGUI(zombiePlayer, humanPlayer, this);
	}

	public void game() {
		turn = gameState.getTurn();
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

	@Override
	public void run() {
		gui.waitTillPlayersAreReady();
		gameState = new GameState(gui);
		System.err.println("Done");
		game();
	}

}
