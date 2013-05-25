package game.controller;

import game.model.Board;
import game.model.Card.CardType;
import game.model.GameState;
import game.model.MoveMaker;
import game.model.Player;
import game.model.Trap;
import game.model.Trap.TrapType;
import game.view.EventReceiver.BoardClickedEvent;
import game.view.EventReceiver.ButtonClickedEvent;
import game.view.EventReceiver.Event;
import game.view.EventReceiver.EventType;
import game.view.GUI;
import game.view.GUI.Button;
import utility.TypedSet;

public class AdvancingStage implements Stage {
	public final GameState gameState;
	public final GUI gui;

	public AdvancingStage(GameState gameState, GUI gui) {
		this.gameState = gameState;
		this.gui = gui;
	}

	public void perform(Player player) {
		Board board = gameState.getBoard();
		switch (player) {
		case ZOMBIE:
			boolean flag=false;
			for (int x = 4; x >= 0; --x)
				for (int y = 0; y < 3; ++y){
					if (board.is(x, y, CardType.ZOMBIE))
						MoveMaker.moveForward(gameState, x, y);
					if(board.is(x, y, CardType.DOGS))
						flag=true;
				}
			if(flag){
				Event event;
				ButtonClickedEvent b;
				BoardClickedEvent c;
				//Selection selection;
				//DogsMover m;
				gui.sendMessage("Time to move dogs");
				gui.setButtonEnabled(Button.EndTurn, true);
				while(true){
					event=gui.eventReceiver.getNextEvent();
					if(event.type==EventType.ButtonClicked){
						b = (ButtonClickedEvent)event;
						if(b.button!=Button.EndTurn)
							continue;
						gui.setButtonEnabled(Button.EndTurn, false);
						break;
					}
					if(event.type==EventType.BoardClicked){
						c = (BoardClickedEvent) event;
						if(!board.is(c.cardClicked.first, c.cardClicked.second, CardType.DOGS)){
							//gui.sendMessage("Not dog");
							continue;
						}
					}
				}
			}
			
			break;
		case HUMAN:
			for (int x = 0; x < 5; ++x)
				for (int y = 0; y < 3; ++y)
					if (board.is(x, y, CardType.BARREL)) {
						if (x == 0) {
							board.set(x, y, null);
						} else {
							TypedSet<Trap, TrapType> traps = board.getTraps(
									x - 1, y);
							if (traps.containsAny(TrapType.WALL, TrapType.CAR)) {
								board.set(x, y, null);
							} else if (traps.contains(TrapType.PIT)) {
								traps.remove(TrapType.PIT);
								board.set(x, y, null);
							} else {
								MoveMaker.moveBackward(gameState, x, y);
							}
						}
					}
			break;
		}
		gameState.update();
	}
}