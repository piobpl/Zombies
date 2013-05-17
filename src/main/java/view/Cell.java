package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.cards.helpers.Card;

public class Cell {

	private JPanel panel;
	private JLabel name;
	private JLabel strength;
	private boolean isHighlighted;

	public Cell(JPanel panel) {
		this.panel = panel;
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(name = new JLabel("Karta"));
		panel.add(strength = new JLabel("Sila: 0"));
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		panel.setPreferredSize(new Dimension(120, 80));
		name.setVisible(false);
		strength.setVisible(false);
		isHighlighted = false;
	}

	public void draw(Card card) {
		if (card == null) {
			name.setVisible(false);
			strength.setVisible(false);
		} else {
			name.setVisible(true);
			name.setText(card.getName());
			if (card.getStrength() == null) {
				strength.setVisible(false);
			} else {
				strength.setVisible(true);
				strength.setText("Sila: " + card.getStrength());
			}
		}
	}

	public void addMouseListener(MouseListener a) {
		panel.addMouseListener(a);
	}

	public void setHighlight(boolean light) {
		isHighlighted = light;
		if (light) {
			panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		} else {
			panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
	}

	public void toggleHighlight() {
		if (isHighlighted) {
			panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		} else {
			panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		}
		isHighlighted = !isHighlighted;
	}
}
