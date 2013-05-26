package lobby.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import lobby.view.Lobby.Button;

public class GameStartPanel {

	private JButton newLocalGameButton;
	private JButton loadLocalGameButton;
	private JButton newNetworkGameButton;
	private JButton loadNetworkGameButton;

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
	}

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
	
	public void addButtonMouseListener(final Button button,
			final MouseListener a) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch (button) {
				case NewLocalGame:
					newLocalGameButton.addMouseListener(a);
					break;
				case LoadLocalGame:
					loadLocalGameButton.addMouseListener(a);
					break;
				case NewNetworkGame:
					newNetworkGameButton.addMouseListener(a);
					break;
				case LoadNetworkGame:
					loadNetworkGameButton.addMouseListener(a);
					break;
				default:
					throw new UnsupportedOperationException();
				}
			}
		});
	}
}
