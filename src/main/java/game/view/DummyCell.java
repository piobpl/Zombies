package game.view;

import game.model.Card;
import game.model.Trap;
import game.view.GUI.Cell;
import game.view.GUIMessage.DrawCellCard;

import java.awt.event.MouseListener;

import javax.swing.JPanel;

import utility.Listener;

public class DummyCell implements Cell{
	
	Listener zombieListener, humanListener;
	int board, row, column;
	
	public DummyCell(Listener zombieListener, Listener humanListener,
			int board, int row, int column) {
		super();
		this.zombieListener = zombieListener;
		this.humanListener = humanListener;
		this.board = board;
		this.row = row;
		this.column = column;
	}

	@Override
	public void drawCard(Card card) {
		zombieListener.send(new DrawCellCard(card, board, row, column));
		humanListener.send(new DrawCellCard(card, board, row, column));
	}

	@Override
	public void drawTraps(Iterable<Trap> traps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMouseListener(MouseListener a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHighlight(boolean light) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRedHighlight(boolean light) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toggleHighlight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerToGlass(JPanel glass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGlassText(String text) {
		// TODO Auto-generated method stub
		
	}

}
