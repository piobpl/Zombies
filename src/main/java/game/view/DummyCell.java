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
import java.util.List;

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
		zombieListener.sendAndWait(new DrawCellCardMessage(card, board, row, column));
		humanListener.sendAndWait(new DrawCellCardMessage(card, board, row, column));
	}

	@Override
	public void drawTraps(List<Trap> traps) {
		zombieListener
				.sendAndWait(new DrawCellTrapsMessage(traps, board, row, column));
		humanListener.sendAndWait(new DrawCellTrapsMessage(traps, board, row, column));
	}

	@Override
	public void addMouseListener(MouseListener a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHighlight(boolean light) {
		zombieListener.sendAndWait(new SetCellHighlightMessage(light, board, row,
				column));
		humanListener.sendAndWait(new SetCellHighlightMessage(light, board, row,
				column));
	}

	@Override
	public void setRedHighlight(boolean light) {
		zombieListener.sendAndWait(new SetCellRedHighlightMessage(light, board, row,
				column));
		humanListener.sendAndWait(new SetCellRedHighlightMessage(light, board, row,
				column));
	}

	@Override
	public void toggleHighlight() {
		zombieListener.sendAndWait(new ToggleCellHighlightMessage(board, row, column));
		humanListener.sendAndWait(new ToggleCellHighlightMessage(board, row, column));
	}

	@Override
	public void registerToGlass(JPanel glass) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setGlassText(String text) {
		zombieListener.sendAndWait(new SetCellGlassTextMessage(board, row, column,
				text));
		humanListener
				.sendAndWait(new SetCellGlassTextMessage(board, row, column, text));
	}

}
