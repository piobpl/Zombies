package menu.view;

import game.controller.LocalController;
import game.model.Player;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import lobby.view.Lobby;

/**
 * A class representing a lobby.
 * 
 * @author krozycki
 * 
 */
public class Menu {

	/**
	 * Enum that represent buttons in lobby.
	 */

	private static JFrame frame;

	public static void main(String args[]) {
		new Menu();
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Lobby");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		Container content = frame.getContentPane();

		content.setLayout(new FlowLayout());
		content.setPreferredSize(new Dimension(200, 150));

		JButton button;

		button = new JButton("New local game");
		button.setPreferredSize(new Dimension(180, 30));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						new LocalController().game(Player.ZOMBIE);
					}
				}).start();
			}
		});
		content.add(button);

		button = new JButton("Load local game");
		button.setPreferredSize(new Dimension(180, 30));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
							localController.game(localController.gameState
									.getPlayer());
						}
					}).start();
				}
			}
		});
		content.add(button);

		button = new JButton("Connect");
		button.setPreferredSize(new Dimension(180, 30));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final String login = (String) JOptionPane.showInputDialog(null,
						"Login:", "Log in", JOptionPane.PLAIN_MESSAGE, null,
						null, null);
				if (login != null) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							new Lobby(login);
						}
					}).start();
				}
			}
		});
		content.add(button);

		button = new JButton("Exit");
		button.setPreferredSize(new Dimension(180, 30));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		frame.getContentPane().add(button);

		frame.pack();
	}
}