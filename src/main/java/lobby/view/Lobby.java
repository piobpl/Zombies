package lobby.view;

import game.view.Colors;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utility.Connector;

// TODO Lobby
public class Lobby {
	public static enum Button {
		NewLocalGame, LoadLocalGame, NewNetworkGame, LoadNetworkGame;
	}

	private JFrame frame;
	private JPanel glass;
	private JPanel lobbyPanel;
	private GameStartPanel gameStartPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Connector connector = Connector.makeConnection("localhost", 8888);
			Scanner scanner = new Scanner(connector.in);
			System.out.println("Otrzymalem: " + scanner.nextLine());
			scanner.close();
			connector.close();
		} catch (IOException e) {
			System.out.println("Cos poszlo nie tak :/");
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lobby window = new Lobby();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Lobby() {
		initialize();
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
		glass = new JPanel();
		glass.setLayout(null);
		frame.setGlassPane(glass);
		glass.setVisible(true);
		glass.setOpaque(false);

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

	public void setButtonEnabled(Button button, boolean active) {
		gameStartPanel.setButtonEnabled(button, active);
	}

	public void addButtonMouseListener(Button button, MouseListener a) {
		gameStartPanel.addButtonMouseListener(button, a);
	}

	public void exit() {
		try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					frame.dispose();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
