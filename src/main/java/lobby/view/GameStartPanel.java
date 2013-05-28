package lobby.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import lobby.view.Lobby.Button;

/**
 * A class that is responsible for buttons in lobby.
 * 
 * @author krozycki
 * 
 */
public class GameStartPanel {

	private JButton newLocalGameButton;
	private JButton loadLocalGameButton;
	private JButton newNetworkGameButton;
	private JButton loadNetworkGameButton;

	/**
	 * Creates new GameStartPanel on the selected panel.
	 * 
	 * @param panel
	 *            selected panel
	 */
	GameStartPanel(JPanel panel) {
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension(390, 290));
		newLocalGameButton = new JButton("New local game");
		newLocalGameButton.setPreferredSize(new Dimension(300, 30));
		panel.add(newLocalGameButton);
		loadLocalGameButton = new JButton("Load local game");
		loadLocalGameButton.setPreferredSize(new Dimension(300, 30));
		panel.add(loadLocalGameButton);
		newNetworkGameButton = new JButton("New network game");
		newNetworkGameButton.setPreferredSize(new Dimension(300, 30));
		panel.add(newNetworkGameButton);
		loadNetworkGameButton = new JButton("Load network game");
		loadNetworkGameButton.setPreferredSize(new Dimension(300, 30));
		panel.add(loadNetworkGameButton);
		newLocalGameButton.addMouseListener(Lobby.Button.NewLocalGame);
		loadLocalGameButton.addMouseListener(Lobby.Button.LoadLocalGame);
		newNetworkGameButton.addMouseListener(Lobby.Button.NewNetworkGame);
		loadNetworkGameButton.addMouseListener(Lobby.Button.LoadNetworkGame);		
	}

	/**
	 * Enables/disables the selected button.
	 * 
	 * @param button
	 *            selected button
	 * @param active
	 *            new button status
	 */
	public void setButtonEnabled(final Button button, final boolean active) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch (button) {
				case NewLocalGame:
					newLocalGameButton.setEnabled(active);
					break;
				case LoadLocalGame:
					loadLocalGameButton.setEnabled(active);
					break;
				case NewNetworkGame:
					newNetworkGameButton.setEnabled(active);
					break;
				case LoadNetworkGame:
					loadNetworkGameButton.setEnabled(active);
					break;
				default:
					throw new UnsupportedOperationException();
				}
			}
		});
	}

	/**
	 * Adds MouseListener for the selected button.
	 * 
	 * @param button
	 *            selected button
	 * @param mouselistener
	 *            MouseListener to be added
	 */
	public void addButtonMouseListener(final Button button,
			final MouseListener mouselistener) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch (button) {
				case NewLocalGame:
					newLocalGameButton.addMouseListener(mouselistener);
					break;
				case LoadLocalGame:
					loadLocalGameButton.addMouseListener(mouselistener);
					break;
				case NewNetworkGame:
					newNetworkGameButton.addMouseListener(mouselistener);
					break;
				case LoadNetworkGame:
					loadNetworkGameButton.addMouseListener(mouselistener);
					break;
				default:
					throw new UnsupportedOperationException();
				}
			}
		});
	}
}
