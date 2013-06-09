package game.view;

import game.model.Card;
import game.model.Trap;
import game.view.GUI.Cell;
import game.view.GUIMessage.DrawCellCardMessage;
import game.view.GUIMessage.DrawCellTrapsMessage;
import game.view.GUIMessage.SetCellGlassTextMessage;
import game.view.GUIMessage.SetCellHighlightMessage;
import game.view.GUIMessage.SetCellRedHighlightMessage;
import game.view.GUIMessage.ToggleCellHighlightMessage;

import java.awt.event.MouseListener;

import javax.swing.JPanel;

import utility.Listener;

public class DummyCell implements Cell {

	Listener zombieListener, humanListener;
	int board, row, column;

	public DummyCell(Listener zombieListener, Listener humanListener,
			int board, int row, int column) {
		this.zombieListener = zombieListener;
		this.humanListener = humanListener;
		this.board = board;
		this.row = row;
		this.column = column;
	}

	@Override
	public void drawCard(Card card) {
		zombieListener.send(new DrawCellCardMessage(card, board, row, column));
		humanListener.send(new DrawCellCardMessage(card, board, row, column));
	}

	@Override
	public void drawTraps(Iterable<Trap> traps) {
		System.out.println("Trapy do narysowania:");
		for (Trap t : traps)
			System.out.println(t);
		zombieListener
				.send(new DrawCellTrapsMessage(traps, board, row, column));
		humanListener.send(new DrawCellTrapsMessage(traps, board, row, column));
	}

	@Override
	public void addMouseListener(MouseListener a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHighlight(boolean light) {
		zombieListener.send(new SetCellHighlightMessage(light, board, row,
				column));
		humanListener.send(new SetCellHighlightMessage(light, board, row,
				column));
	}

	@Override
	public void setRedHighlight(boolean light) {
		zombieListener.send(new SetCellRedHighlightMessage(light, board, row,
				column));
		humanListener.send(new SetCellRedHighlightMessage(light, board, row,
				column));
	}

	@Override
	public void toggleHighlight() {
		zombieListener.send(new ToggleCellHighlightMessage(board, row, column));
		humanListener.send(new ToggleCellHighlightMessage(board, row, column));
	}

	@Override
	public void registerToGlass(JPanel glass) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setGlassText(String text) {
		zombieListener.send(new SetCellGlassTextMessage(board, row, column,
				text));
		humanListener
				.send(new SetCellGlassTextMessage(board, row, column, text));
	}

}
