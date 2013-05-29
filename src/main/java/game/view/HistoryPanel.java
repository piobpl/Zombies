package game.view;

import game.view.GUI.Button;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class HistoryPanel {

	private JButton saveButton;

	public HistoryPanel(JPanel panel) {
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension(390, 120));

		saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(120, 30));
		panel.add(saveButton);
	}

	public void addButtonMouseListener(final Button button,
			final MouseListener a) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch (button) {
				case Save:
					saveButton.addMouseListener(a);
					break;
				default:
					throw new UnsupportedOperationException();
				}
			}
		});
	}
}
