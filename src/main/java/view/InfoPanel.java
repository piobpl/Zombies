package view;

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
	
	InfoPanel(JPanel panel){
		
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
		scrollPane.setBorder(BorderFactory.createEmptyBorder(50,10,10,10));
		panel.add(scrollPane);
		textArea.setEditable(false);
		
	}
	
	public void sendMessage(String message){
		textArea.append(message);
		textArea.append("\n");
	}
	
	public void addButtonMouseListener(Button button, MouseListener a) {
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
}
