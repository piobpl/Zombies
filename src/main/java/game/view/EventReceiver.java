package game.view;

import game.model.Player;
import game.view.GUI.Button;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
		BoardClicked, HandClicked, ButtonClicked;
	}

	public abstract static class Event {
		public final EventType type;
		public final MouseEvent info;

		public Event(EventType type, MouseEvent info) {
			this.type = type;
			this.info = info;
		}
	}

	public static class BoardClickedEvent extends Event {
		public final Pair<Integer, Integer> cardClicked;

		public BoardClickedEvent(Pair<Integer, Integer> cardClicked,
				MouseEvent info) {
			super(EventType.BoardClicked, info);
			this.cardClicked = cardClicked;
		}
	}

	public static class HandClickedEvent extends Event {
		public final Integer cardClicked;
		public final Player player;

		public HandClickedEvent(Integer cardClicked, Player player,
				MouseEvent info) {
			super(EventType.HandClicked, info);
			this.cardClicked = cardClicked;
			this.player = player;
		}
	}

	public static class ButtonClickedEvent extends Event {
		public final Button button;

		public ButtonClickedEvent(Button button, MouseEvent info) {
			super(EventType.ButtonClicked, info);
			this.button = button;
		}
	}

	private static class SimpleMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

	private final GUI gui;
	private final BlockingQueue<Event> eventQueue;

	public EventReceiver(GUI gui) {
		this.gui = gui;
		eventQueue = new ArrayBlockingQueue<>(32);
		registerToHand(Player.HUMAN);
		registerToHand(Player.ZOMBIE);
		registerToBoard();
		registerToButtons();
	}

	private void registerToHand(final Player player) {
		for (int i = 0; i < 4; ++i) {
			final int index = i;
			gui.getHand(player).getCell(i)
					.addMouseListener(new SimpleMouseListener() {
						@Override
						public void mouseClicked(MouseEvent e) {
							try {
								eventQueue.put(new HandClickedEvent(index,
										player, e));
								System.err.println("" + e.getButton()
										+ " clicked: " + index + " on "
										+ player + " hand");
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
						.addMouseListener(new SimpleMouseListener() {
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
			gui.addButtonMouseListener(button, new SimpleMouseListener() {
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
	public Event getNextEvent() {
		try {
			return eventQueue.take();
		} catch (InterruptedException e) {
			System.err.println("Waiting for next event interrupted");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Funkcja czeka, aż użytkownik wciśnie podany button, ignorując pozostałe
	 * eventy.
	 */
	public void waitForClick(Button button) {
		Event event;
		while (true) {
			event = getNextEvent();
			if (event.type == EventType.ButtonClicked
					&& event.info.getButton() == MouseEvent.BUTTON1)
				if (((ButtonClickedEvent) event).button == button)
					break;
		}
	}

}
