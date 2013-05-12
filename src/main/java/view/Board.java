package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class Board {
	
	private Cell[][] cells = new Cell[5][3];  
	
	public Board(JPanel panel){
		panel.setLayout(new GridLayout(5, 3, 5, 5));
		for (int i = 0; i < 15; ++i) {
			JPanel cellPanel = new JPanel();
			cells[i/3][i%3] = new Cell(cellPanel);
			panel.add(cellPanel);
		}
	}
	
	/**
	 * Returns a cell at a specified position.
	 * 
	 * @param x
	 *            row
	 * @param y
	 *            column
	 * @return a cell at a specified position
	 */
	public Cell getCell(int x, int y){
		return cells[x][y];
	}
}
