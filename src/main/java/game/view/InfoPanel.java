package game.view;

import game.view.GUI.Button;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class InfoPanel {

	private JButton applySelectionButton;
	private JButton cancelSelectionButton;
	private JButton endTurnButton;
	private JTextArea textArea;

	InfoPanel(JPanel panel) {

		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension(390, 290));

		applySelectionButton = new JButton("Apply");
		applySelectionButton.setPreferredSize(new Dimension(120, 30));

		panel.add(applySelectionButton);

		cancelSelectionButton = new JButton("Cancel");
		cancelSelectionButton.setPreferredSize(new Dimension(120, 30));
		panel.add(cancelSelectionButton);

		endTurnButton = new JButton("End turn");
		endTurnButton.setPreferredSize(new Dimension(120, 30));
		panel.add(endTurnButton);

		textArea = new JTextArea(12, 32);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createMatteBorder(50, 10, 10, 10, Colors.margines.getColor()));
		panel.add(scrollPane);
		textArea.setBackground(Colors.jasnaOliwka.getColor());
		textArea.setForeground(Colors.humansCard.getColor());
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Colors.humansCard.getColor()));
	}

	public void setButtonEnabled(final Button button, final boolean aktywny){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch (button) {
				case ApplySelection:
					applySelectionButton.setEnabled(aktywny);
					break;
				case CancelSelection:
					cancelSelectionButton.setEnabled(aktywny);
					break;
				case EndTurn:
					endTurnButton.setEnabled(aktywny);
					break;
				default:
					throw new UnsupportedOperationException();
				}
			}
		});
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