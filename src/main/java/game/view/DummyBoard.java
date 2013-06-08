package game.view;

import game.view.GUI.Board;
import game.view.GUI.Cell;
import game.view.GUIMessage.ClearBoardGlassTextMessage;
import game.view.GUIMessage.SetBoardColumnHighlightMessage;
import game.view.GUIMessage.SetBoardHighlightMessage;
import game.view.GUIMessage.SetBoardRowHighlightMessage;

import javax.swing.JPanel;

import utility.Listener;

public class DummyBoard implements Board{

	Listener zombieListener, humanListener;
	
	public DummyBoard(Listener zombieListener, Listener humanListener) {
		this.zombieListener = zombieListener;
		this.humanListener = humanListener;
	}

	@Override
	public Cell getCell(int x, int y) {
		return new DummyCell(zombieListener, humanListener, 1, x, y);
	}

	@Override
	public void setHighlight(boolean set) {
		zombieListener.send(new SetBoardHighlightMessage(set));
		humanListener.send(new SetBoardHighlightMessage(set));
	}

	@Override
	public void setColumnHighlight(int j, boolean set) {
		zombieListener.send(new SetBoardColumnHighlightMessage(j, set));
		humanListener.send(new SetBoardColumnHighlightMessage(j, set));
	}

	@Override
	public void setRowHighlight(int i, boolean set) {
		zombieListener.send(new SetBoardRowHighlightMessage(i, set));
		humanListener.send(new SetBoardRowHighlightMessage(i, set));
	}

	@Override
	public void registerToGlass(JPanel glass) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearGlassText() {
		zombieListener.send(new ClearBoardGlassTextMessage());
		humanListener.send(new ClearBoardGlassTextMessage());
	}
}
