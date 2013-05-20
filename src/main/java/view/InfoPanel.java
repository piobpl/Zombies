package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import view.GUI.Button;

public class InfoPanel {

	private JButton applySelectionButton;
	private JButton cancelSelectionButton;
	private JButton endTurnButton;
	private JTextArea textArea;

	InfoPanel(JPanel panel) {

		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension(400, 300));

		applySelectionButton = new JButton("Apply Selection");
		applySelectionButton.setPreferredSize(new Dimension(120, 30));
		panel.add(applySelectionButton);

		cancelSelectionButton = new JButton("Cancel Selection");
		cancelSelectionButton.setPreferredSize(new Dimension(120, 30));
		panel.add(cancelSelectionButton);

		endTurnButton = new JButton("End turn");
		endTurnButton.setPreferredSize(new Dimension(120, 30));
		panel.add(endTurnButton);

		textArea = new JTextArea(18, 34);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createMatteBorder(50, 10, 10, 10, new Color(180,202,157)));
		panel.add(scrollPane);
		textArea.setBackground(new Color(180,202,157));
		textArea.setForeground(new Color(136,38,38));
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(80,119,97)));
	}

	public void sendMessage(final String message) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textArea.append(message);
				textArea.append("\n");
			}
		});
	}

	public void addButtonMouseListener(final Button button, final MouseListener a) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch (button) {
				case ApplySelection:
					applySelectionButton.addMouseListener(a);
					break;
				case CancelSelection:
					cancelSelectionButton.addMouseListener(a);
					break;
				case EndTurn:
					endTurnButton.addMouseListener(a);
					break;
				default:
					throw new UnsupportedOperationException();
				}
			}
		});
	}
}
