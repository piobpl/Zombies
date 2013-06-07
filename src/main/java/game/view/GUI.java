package game.view;

import game.model.Card;
import game.model.Modifier;
import game.model.Player;
import game.model.Trap;

import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

public interface GUI {
	public static enum Button {
		ApplySelection, CancelSelection, EndTurn, Save, Command;
	}

	public void setPlayer(Player player);

	public void setButtonEnabled(Button button, boolean aktywny);

	public Hand getHand(Player player);

	public Board getBoard();

	public EventReceiver getEventReceiver();

	public void addButtonMouseListener(Button button, MouseListener a);
	
	public void addSliderChangeListener(ChangeListener cl);

	public void setCardsLeft(Player player, int left);

	public void modelSendsMessage(final String message);

	public void modelSendsAllMessages(final List<String> messages);

	public void drawGlobalModifiers(final Iterable<Modifier> modifiers);
	
	public void drawHistorySlider(int turn);

	public void setHighlight(boolean set);

	public void exit();

	public static interface Hand {
		public Cell getCell(int x);

		public void setHighlight(boolean set);
	}

	public static interface Board {
		public Cell getCell(int x, int y);

		public void setHighlight(boolean set);

		public void setColumnHighlight(int j, boolean set);

		public void setRowHighlight(int i, boolean set);

		public void registerToGlass(JPanel glass);

		public void clearGlassText();
	}

	public static interface Cell {
		public void drawCard(final Card card);

		public void drawTraps(final Iterable<Trap> traps);

		public void addMouseListener(final MouseListener a);

		public void setHighlight(final boolean light);

		public void setRedHighlight(final boolean light);

		public void toggleHighlight();

		public void registerToGlass(JPanel glass);

		public void setGlassText(String text);
	}
}
