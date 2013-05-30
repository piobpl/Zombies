package game.controller;

import game.model.Board;
import game.model.Card;
import game.model.Card.CardType;
import game.model.GameState;
import game.model.Modifier.ModifierType;
import game.model.MoveMaker;
import game.model.Player;
import game.model.Trap;
import game.model.Trap.TrapType;
import game.model.cards.zombies.DogsMover;
import game.view.EventReceiver;
import game.view.EventReceiver.BoardClickedEvent;
import game.view.EventReceiver.ButtonClickedEvent;
import game.view.EventReceiver.Event;
import game.view.EventReceiver.EventType;
import game.view.GUI;
import game.view.GUI.Button;
import utility.Pair;
import utility.TypedSet;

/**
 *
 * @author Edoipi
 *
 */
public class AdvancingStage implements Stage {
	public final GameState gameState;
	public final GUI gui;
	private Selector selector;

	public AdvancingStage(GameState gameState, GUI gui) {
		this.gameState = gameState;
		this.gui = gui;
		selector = new Selector(gameState, gui);
	}

	public static Pair<Integer, Integer> askForUseOfNotSoFast(
			GameState gameState) {
		int pos = -1;
		for (int i = 0; i < 4; ++i) {
			Card c = gameState.getHand(Player.ZOMBIE).get(i);
			if (c != null && c.getType() == CardType.NOTSOFAST)
				pos = i;
		}
		if (pos == -1)
			return null;
		gameState.sendMessage("Do you want to use not so fast?");
		GUI gui = gameState.gui;
		gui.setButtonEnabled(Button.CancelSelection, true);
		EventReceiver events = gameState.gui.getEventReceiver();
		Pair<Integer, Integer> zombie = null, candidate;
		try {
			while (true) {
				Event e = events.getNextClickEvent();
				if (e.type == EventType.ButtonClicked) {
					if (((ButtonClickedEvent) e).button == Button.ApplySelection
							&& zombie != null) {
						gameState.getHand(Player.ZOMBIE).set(pos, null);
						return zombie;
					}
					if (((ButtonClickedEvent) e).button == Button.CancelSelection)
						return null;
				} else if (e.type == EventType.BoardClicked) {
					candidate = ((BoardClickedEvent) e).cardClicked;
					Card c = gameState.getBoard().get(candidate.first,
							candidate.second);
					if (c != null && c.getType() == CardType.ZOMBIE) {
						if (zombie != null)
							gui.getBoard().getCell(zombie.first, zombie.second)
									.setHighlight(false);
						zombie = candidate;
						gui.getBoard().getCell(zombie.first, zombie.second)
								.setHighlight(true);
						gui.setButtonEnabled(Button.ApplySelection, true);
					}
				}
			}
		} finally {
			if (zombie != null)
				gui.getBoard().getCell(zombie.first, zombie.second)
						.setHighlight(false);
			gui.setButtonEnabled(Button.ApplySelection, false);
			gui.setButtonEnabled(Button.CancelSelection, false);
		}
	}

	public void perform(Player player) {
		Board board = gameState.getBoard();
		switch (player) {
		case ZOMBIE:
			Pair<Integer, Integer> zombie = askForUseOfNotSoFast(gameState);

			int flag = 0;
			for (int x = 4; x >= 0; --x)
				for (int y = 0; y < 3; ++y) {
					if (board.is(x, y, CardType.DOGS)) {
						flag++;
					}
				}

			if (flag > 0) {
				boolean moved[][] = new boolean[5][3];
				Event event;
				ButtonClickedEvent b;
				BoardClickedEvent c;
				Selection selection;
				DogsMover m;
				gameState.sendMessage("Time to move dogs");
				gui.setButtonEnabled(Button.EndTurn, true);
				while (true) {
					event = gui.getEventReceiver().getNextClickEvent();
					if (event.type == EventType.ButtonClicked) {
						b = (ButtonClickedEvent) event;
						if (b.button != Button.EndTurn)
							continue;
						gui.setButtonEnabled(Button.EndTurn, false);
						gui.setHighlight(false);
						break;
					}
					if (event.type == EventType.BoardClicked) {
						c = (BoardClickedEvent) event;
						if (!board.is(c.cardClicked.first,
								c.cardClicked.second, CardType.DOGS)
								|| moved[c.cardClicked.first][c.cardClicked.second]) {
							continue;
						}
						gui.getBoard()
								.getCell(c.cardClicked.first,
										c.cardClicked.second)
								.setHighlight(true);
						m = new DogsMover(c.cardClicked);
						selection = selector.getSelection(m);
						if (selection != null) {
							System.err.println("Selection received, applying.");
							m.makeEffect(selection, gameState);
							if (m.endOfPath != null) {
								moved[m.endOfPath.first][m.endOfPath.second] = true;
							}
							flag--;
						}
						gameState.update();
						gui.setHighlight(false);

						for (int x = 4; x >= 0; --x)
							for (int y = 0; y < 3; ++y) {
								if (moved[x][y]) {
									gui.getBoard().getCell(x, y)
											.setRedHighlight(true);
								}
							}

						if (flag == 0) {
							gui.setHighlight(false);
							break;
						}
					}
				}
			}
			for (int x = 4; x >= 0; --x)
				for (int y = 0; y < 3; ++y) {
					if (board.is(x, y, CardType.ZOMBIE)
							&& (zombie == null || zombie.first != x || zombie.second != y))
						MoveMaker.moveForward(gameState, x, y);
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
							} else if (gameState.getBoard().get(x - 1, y)
									.getModifiers()
									.contains(ModifierType.HUMAN)) {
								gameState.getBoard().get(x - 1, y)
										.getModifiers()
										.remove(ModifierType.HUMAN);
								board.set(x, y, null);
							} else {
								Card temp = gameState.getBoard().get(x - 1, y);
								gameState.getBoard().remove(x - 1, y);
								if (MoveMaker.isMovePossible(gameState,
										new Pair<Integer, Integer>(x, y),
										new Pair<Integer, Integer>(x - 1, y),
										null)) {
									MoveMaker.moveBackward(gameState, x, y);
									if (temp != null)
										gameState.getBoard().remove(x - 1, y);
								} else
									gameState.getBoard().set(x - 1, y, temp);
							}
						}
					}
			break;
		}
		gameState.update();
	}

	@Override
	public StageType getStageType() {
		return StageType.ADVANCING;
	}
}
