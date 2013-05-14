package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class Hand {

	private Cell[] cells = new Cell[4];

	public Hand(JPanel panel) {
		panel.setLayout(new GridLayout(1, 4, 5, 5));
		for (int i = 0; i < 4; ++i) {
			JPanel cellPanel = new JPanel();
			cells[i] = new Cell(cellPanel);
			panel.add(cellPanel);
		}
	}

	public Cell getCell(int x) {
		return cells[x];
	}

	public void setHighlight(boolean set) {
		for (int i = 0; i < 4; ++i)
			cells[i].setHighlight(set);
	}

}
