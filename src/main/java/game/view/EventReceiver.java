package game.view;

import game.model.GameState;
import game.model.Player;
import game.view.GUI.Button;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import utility.Pair;

/**
 * Klasa służaca do komunikacji z użytkownikiem.
 *
 * @author piob
 */
public class EventReceiver {

	public static enum EventType {
		BoardClicked, HandClicked, ButtonClicked, Trigger;
	}

	public abstract static class Event {
		public final EventType type;

		public Event(EventType type) {
			this.type = type;
		}
	}

	public abstract static class ClickEvent extends Event {
		public final MouseEvent info;

		public ClickEvent(EventType type, MouseEvent info) {
			super(type);
			this.info = info;
		}
	}

	public static class BoardClickedEvent extends ClickEvent {
		public final Pair<Integer, Integer> cardClicked;

		public BoardClickedEvent(Pair<Integer, Integer> cardClicked,
				MouseEvent info) {
			super(EventType.BoardClicked, info);
			this.cardClicked = cardClicked;
		}
	}

	public static class HandClickedEvent extends ClickEvent {
		public final Integer cardClicked;
		public final Player player;

		public HandClickedEvent(Integer cardClicked, Player player,
				MouseEvent info) {
			super(EventType.HandClicked, info);
			this.cardClicked = cardClicked;
			this.player = player;
		}
	}

	public static class ButtonClickedEvent extends ClickEvent {
		public final Button button;

		public ButtonClickedEvent(Button button, MouseEvent info) {
			super(EventType.ButtonClicked, info);
			this.button = button;
		}
	}

	public abstract static class TriggerEvent extends Event {

		public TriggerEvent() {
			super(EventType.Trigger);
		}

		public abstract void trigger(GameState gameState);

	}

	public static interface TriggerEventHandler {
		public void receiveTriggerEvent(TriggerEvent e);
	}

	private final GUI gui;
	private final BlockingQueue<Event> eventQueue;
	private final TriggerEventHandler triggerHandler;

	public EventReceiver(GUI gui, TriggerEventHandler triggerHandler) {
		this.gui = gui;
		this.triggerHandler = triggerHandler;
		eventQueue = new ArrayBlockingQueue<>(32);
		registerToHand(Player.HUMAN);
		registerToHand(Player.ZOMBIE);
		registerToBoard();
		registerToButtons();
	}

	private void registerToHand(final Player player) {
		for (int i = 0; i < 4; ++i) {
			final int index = i;
			gui.getHand(player).getCell(i).addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						eventQueue.put(new HandClickedEvent(index, player, e));
						System.err.println("" + e.getButton() + " clicked: "
								+ index + " on " + player + " hand");
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
	}

	private void registerToBoard() {
		for (int i = 0; i < 5; i++) {
			final int row = i;
			for (int j = 0; j < 3; j++) {
				final int col = j;
				gui.getBoard().getCell(row, col)
						.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								try {
									eventQueue
											.put(new BoardClickedEvent(
													new Pair<Integer, Integer>(
															row, col), e));
									System.err.println("" + e.getButton()
											+ " clicked board at (" + row
											+ ", " + col + ")");
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
							}
						});
			}
		}
	}

	private void registerToButtons() {
		for (Button i : Button.values()) {
			final Button button = i;
			gui.addButtonMouseListener(button, new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						eventQueue.put(new ButtonClickedEvent(button, e));
						System.err.println("" + e.getButton() + " " + button
								+ " clicked");
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
	}

	/**
	 * Funkcja zwraca kolejną akcje wykonaną przez użytkownika. Jeżeli
	 * użytkownik nie podjął żadnego działania, blokuje wątek w oczekiwaniu.
	 * Jeżeli zostanie przerwana, wypisuje o tym informacje na stderr i zwraca
	 * null.
	 */
	public ClickEvent getNextClickEvent() {
		try {
			while (true) {
				Event e = eventQueue.take();
				if (e.type == EventType.Trigger)
					triggerHandler.receiveTriggerEvent((TriggerEvent) e);
				else
					return (ClickEvent) e;
			}
		} catch (InterruptedException e) {
			System.err.println("Waiting for next event interrupted");
			e.printStackTrace();
			return null;
		}
	}

}
