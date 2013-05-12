package view;

import java.awt.Color;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Card;

public class Cell {

	private JPanel panel;
	private JLabel name;
	private JLabel strength;
	
	public Cell(JPanel panel) {
		this.panel = panel;
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(name = new JLabel("Karta"));
		panel.add(strength = new JLabel("Sila: 0"));
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		panel.setVisible(false);
	}
	
	public void draw(Card card){
		if(card == null){
			panel.setVisible(false);
		} else {
			panel.setVisible(true);
			name.setText(card.getName());
			if(card.getStrength() == null){
				strength.setVisible(false);
			} else {
				strength.setVisible(true);
				strength.setText("Sila: " + card.getStrength());
			}
		}
	}
	
	public void addActionListener(MouseListener a){
	    panel.addMouseListener(a);
	}
	
}
