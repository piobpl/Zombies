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
		zombieListener.sendAndWait(new SetBoardHighlightMessage(set));
		humanListener.sendAndWait(new SetBoardHighlightMessage(set));
	}

	@Override
	public void setColumnHighlight(int j, boolean set) {
		zombieListener.sendAndWait(new SetBoardColumnHighlightMessage(j, set));
		humanListener.sendAndWait(new SetBoardColumnHighlightMessage(j, set));
	}

	@Override
	public void setRowHighlight(int i, boolean set) {
		zombieListener.sendAndWait(new SetBoardRowHighlightMessage(i, set));
		humanListener.sendAndWait(new SetBoardRowHighlightMessage(i, set));
	}

	@Override
	public void registerToGlass(JPanel glass) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearGlassText() {
		zombieListener.sendAndWait(new ClearBoardGlassTextMessage());
		humanListener.sendAndWait(new ClearBoardGlassTextMessage());
	}
}
