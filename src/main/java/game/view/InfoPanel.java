package game.view;

import game.model.Modifier;
import game.view.GUI.Button;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class InfoPanel {

	private JButton applySelectionButton;
	private JButton cancelSelectionButton;
	private JButton endTurnButton;
	private JButton saveGameButton;
	private JTextArea textArea;
	private JLabel modyfikator;
	private JPanel panelBrazowy;

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
		
		saveGameButton = new JButton("Save game");
		saveGameButton.setPreferredSize(new Dimension(120,30));
		panel.add(saveGameButton);

		panelBrazowy = new JPanel();
		panelBrazowy.setBackground(Colors.margines.getColor());
		panelBrazowy.setForeground(Colors.margines.getColor());
		panelBrazowy
				.setLayout(new BoxLayout(panelBrazowy, BoxLayout.PAGE_AXIS));
		panelBrazowy.setVisible(true);
		panel.add(panelBrazowy);

		modyfikator = new JLabel();
		modyfikator.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		modyfikator.setHorizontalTextPosition(SwingConstants.CENTER);
		modyfikator.setPreferredSize(new Dimension(14, 32));
		modyfikator.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10,
				Colors.margines.getColor()));
		panelBrazowy.add(modyfikator);

		textArea = new JTextArea(12, 30);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10,
				Colors.margines.getColor()));

		panelBrazowy.setBorder(BorderFactory.createMatteBorder(15, 5, 10, 5,
				Colors.boardsCard.getColor()));
		panelBrazowy.add(scrollPane);
		textArea.setBackground(Colors.jasnaOliwka.getColor());
		textArea.setForeground(Colors.humansCard.getColor());
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Colors.humansCard.getColor()));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
	}

	public void drawGlobalModifiers(final Iterable<Modifier> modifiers) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String modifier = "";
				boolean first = true;
				for (Modifier m : modifiers) {
					if (!first)
						modifier += ", ";
					modifier += m.getName();
					first = false;
				}
				modyfikator.setText(modifier);
				modyfikator.setForeground(Colors.tlo.getColor());
			}
		});
	}

	public void setButtonEnabled(final Button button, final boolean aktywny) {
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
				case SaveGame:
					saveGameButton.setEnabled(aktywny);
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
	
	public void sendAllMessages(final List<String> messages) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textArea.setText("");
				for(int i=0;i<messages.size()-3;i++){
					textArea.append(messages.get(i));
					textArea.append("\n");
				}
				textArea.append("--------Game successfully loaded-------\n");
			}
		});
	}

	public void addButtonMouseListener(final Button button,
			final MouseListener a) {
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
				case SaveGame:
					saveGameButton.addMouseListener(a);
					break;
				default:
					throw new UnsupportedOperationException();
				}
			}
		});
	}
}
