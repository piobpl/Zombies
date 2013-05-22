package game.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class Board {

	private Cell[][] cells = new Cell[5][3];

	public Board(JPanel panel, Color colorKarty, Color colorTla) {
		panel.setLayout(new GridLayout(5, 3, 5, 5));
		for (int i = 0; i < 15; ++i) {
			JPanel cellPanel = new JPanel();
			cells[i / 3][i % 3] = new Cell(cellPanel, colorKarty, colorTla);
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
	public Cell getCell(int x, int y) {
		return cells[x][y];
	}

	public void setHighlight(boolean set) {
		for (int i = 0; i < 5; ++i)
			for (int j = 0; j < 3; ++j)
				cells[i][j].setHighlight(set);
	}

	public void setColumnHighlight(int j, boolean set) {
		for (int i = 0; i < 5; ++i)
			cells[i][j].setHighlight(set);
	}

	public void setRowHighlight(int i, boolean set) {
		for (int j = 0; j < 3; ++j)
			cells[i][j].setHighlight(set);
	}

	public void registerToGlass(JPanel glass) {
		for (int i = 0; i < 5; ++i)
			for (int j = 0; j < 3; ++j)
				cells[i][j].registerToGlass(glass);
	}

	public void clearGlassText(){
		for (int i = 0; i < 5; ++i)
			for (int j = 0; j < 3; ++j)
				cells[i][j].setGlassText("");
	}
}
