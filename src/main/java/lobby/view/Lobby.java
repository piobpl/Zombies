package lobby.view;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

import utility.Connector;

// TODO Lobby
public class Lobby {

	private JFrame frame;

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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
