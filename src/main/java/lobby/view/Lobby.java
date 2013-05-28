package lobby.view;

import game.controller.LocalController;
import game.view.Colors;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A class representing a lobby.
 * 
 * @author krozycki
 * 
 */
public class Lobby {

	/**
	 * Enum that represent buttons in lobby.
	 */
	public static enum Button implements MouseListener {
		NewLocalGame {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.err.println("Clicked");
				if (e.getButton() == MouseEvent.BUTTON1) {
					System.err.println(" via BUTTON1");
					Object source = e.getSource();
					if (source != null) {
						System.err.println("Starting new local game");
						exit();
						new Thread(new Runnable() {
							public void run() {
								new LocalController().game();
							}
						}).start();
					}
				}
			}
		},
		LoadLocalGame {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.err.println("Loading saved local game");
				exit();
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);
				byte[] save = null;
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						save = Files.readAllBytes(fileChooser.getSelectedFile()
								.toPath());
					} catch (IOException er) {
						System.err.println("Save loading error!");
						er.printStackTrace();
					}
				}
				if (save != null) {
					final byte[] finalSave = save;
					new Thread(new Runnable() {
						public void run() {
							LocalController localController = new LocalController();
							localController.gameState.load(finalSave);
							localController.gameLoader();
						}
					}).start();
				}
			}
		},
		NewNetworkGame {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.err.println("Starting new network game");
				exit();
				// TODO
			}
		},
		LoadNetworkGame {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.err.println("Loading saved network game");
				exit();
				// TODO
			}
		};

		@Override
		public abstract void mouseClicked(MouseEvent e);

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		public void exit() {
			frame.dispose();
		}
	}

	private static JFrame frame;
	private static JPanel lobbyPanel;
	private static GameStartPanel gameStartPanel;

	/**
	 * Create the application.
	 */
	public Lobby() {
		initialize();
	}

	/**
	 * Enables/disables the selected button.
	 * 
	 * @param button
	 *            selected button
	 * @param active
	 *            new button status
	 */
	public void setButtonEnabled(Button button, boolean active) {
		gameStartPanel.setButtonEnabled(button, active);
	}

	/**
	 * Adds MouseListener for the selected button.
	 * 
	 * @param button
	 *            selected button
	 * @param mouselistener
	 *            MouseListener to be added
	 */
	public void addButtonMouseListener(Button button, MouseListener a) {
		gameStartPanel.addButtonMouseListener(button, a);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Lobby");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridBagLayout());
		frame.getContentPane().setBackground(Colors.tlo.getColor());
		frame.setResizable(false);

		GridBagConstraints gbc;

		lobbyPanel = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 10, 10, 10);
		lobbyPanel.setBackground(Colors.boardsCard.getColor());
		lobbyPanel.setForeground(Colors.boardsCard.getColor());
		frame.getContentPane().add(lobbyPanel, gbc);

		frame.setVisible(true);

		gameStartPanel = new GameStartPanel(lobbyPanel);
		frame.pack();
	}

}
