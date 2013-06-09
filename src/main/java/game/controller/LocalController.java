package game.controller;

import game.model.GameState;
import game.model.Player;
import game.view.EventReceiver;
import game.view.EventReceiver.Event;
import game.view.EventReceiver.TriggerEvent;
import game.view.EventReceiver.TriggerEventHandler;
import game.view.GUI;
import game.view.GUI.Button;
import game.view.HistoryPanel;
import game.view.SimpleGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LocalController implements TriggerEventHandler {

	public final GameState gameState;
	public final GUI gui;
	private int turn;
	private final Freeze myFilter;

	private static class Freeze implements EventReceiver.Filter {
		@Override
		public boolean acceptable(Event event) {
			return false;
		}

	}

	public LocalController() {
		System.err.println("Creating Controller...");
		myFilter = new Freeze();
		gui = new SimpleGUI(this, null);
		gameState = new GameState(gui);
		gui.addButtonListener(Button.Save, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveState();
			}
		});

		gui.addSliderChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				HistoryPanel history = gui.getHistory();
				if (!source.getValueIsAdjusting()) {
					int choice = (int) source.getValue();
					System.err.println("Selected: " + choice);
					if (choice != source.getMaximum()) {
						System.err.print("Setting previous version...");
						try {
							if (history.isUpToDate()) {
								history.setUpToDate(false);
								for (Button button : new Button[] {
										Button.ApplySelection,
										Button.CancelSelection, Button.EndTurn,
										Button.Command }) {
									history.setInfoButtonEnabled(button,
											gui.isButtonEnabled(button));
									gui.setButtonEnabled(button, false);
								}
							}
							byte[] previous = gameState.getSave(choice);
							GameState previousGameState;
							previousGameState = gameState.load(previous);
							gui.getEventReceiver().addFilter(myFilter);
							gui.setButtonEnabled(Button.Save, false);
							previousGameState.update();
						} catch (ClassNotFoundException | IOException e1) {
							e1.printStackTrace();
						}
					} else {
						System.err.print("Reloading..");
						gameState.getBoard().update();
						gameState.getHand(Player.HUMAN).update();
						gameState.getHand(Player.ZOMBIE).update();
						gui.getEventReceiver().removeFilter(myFilter);
						gui.setButtonEnabled(Button.Save, true);
						for (Button button : new Button[] {
								Button.ApplySelection, Button.CancelSelection,
								Button.EndTurn, Button.Command })
							gui.setButtonEnabled(button,
									history.isInfoButtonEnabled(button));
						history.setUpToDate(true);
					}
				}
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

	public void game(Player startingPlayer) {
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
		System.err.println("Game started");
		if (startingPlayer == Player.HUMAN) {
			try {
				if (gameState.getPlayer() == Player.HUMAN) {
					gameState.setTurn(turn);
					gameState.sendMessage("Round #" + turn);
					try {
						gameState.setLastSave(gameState.save());
					} catch (IOException e) {
						e.printStackTrace();
					}
					gameState.sendMessage(gameState.getPlayer() + "'s turn.");
					for (Stage s : stages) {
						gameState.setStage(s.getStageType());
						gameState.nextStage();
						s.perform(gameState.getPlayer());
					}
					++turn;
					gameState.setTurn(turn);
				}
			} catch (GameOver gameOver) {
				gameState.sendMessage(gameOver.won + " has won!");
			}
		}
		try {
			while (true) {
				gameState.sendMessage("Round #" + turn);
				for (Player p : players) {
					gameState.setPlayer(p);
					try {
						gameState.setLastSave(gameState.save());
					} catch (IOException e) {
						e.printStackTrace();
					}
					gameState.sendMessage(p + "'s turn.");
					for (Stage s : stages) {
						gameState.setStage(s.getStageType());
						gameState.nextStage();
						s.perform(p);
					}
				}
				++turn;
				gameState.setTurn(turn);
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

	public static void main(String args[]) {
		new LocalController().game(Player.ZOMBIE);
	}

}
