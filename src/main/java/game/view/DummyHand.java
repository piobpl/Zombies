package game.view;

import game.model.Player;
import game.view.GUI.Cell;
import game.view.GUI.Hand;
import game.view.GUIMessage.SetHandHighlightMessage;
import utility.Listener;

public class DummyHand implements Hand {

	Listener zombieListener, humanListener;
	Player player;

	public DummyHand(Listener zombieListener, Listener humanListener,
			Player player) {
		this.zombieListener = zombieListener;
		this.humanListener = humanListener;
		this.player = player;
	}
	
	@Override
	public Cell getCell(int x) {
		int board;
		if(player == Player.ZOMBIE)
			board = 0;
		else
			board = 2;
		return new DummyCell(zombieListener, humanListener, board, 0, x);
	}

	@Override
	public void setHighlight(boolean set) {
		zombieListener.sendAndWait(new SetHandHighlightMessage(player,set));
		zombieListener.sendAndWait(new SetHandHighlightMessage(player,set));
	}
}
