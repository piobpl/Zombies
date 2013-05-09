package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Cell {
	// TODO
	public Cell(JPanel panel) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(new JLabel("Karta"));
		panel.add(new JLabel("Sila: 0"));
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}
}
