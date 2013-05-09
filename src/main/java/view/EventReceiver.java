package view;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import model.Player;
import utility.Pair;

/**
 * Klasa służaca do komunikacji z użytkownikiem.
 * 
 * @author piob
 */
public class EventReceiver {

	public static enum EventType {
		BoardClicked, HandClicked, ApplyButtonClicked, CancelButtonClicked,
	}

	public abstract static class Event {
		public final EventType type;

		public Event(EventType type) {
			this.type = type;
		}
	}

	public static class BoardClickedEvent extends Event {
		public final Pair<Integer, Integer> cardClicked;

		public BoardClickedEvent(Pair<Integer, Integer> cardClicked) {
			super(EventType.BoardClicked);
			this.cardClicked = cardClicked;
		}
	}

	public static class HandClickedEvent extends Event {
		public final Integer cardClicked;
		public final Player player;

		public HandClickedEvent(Integer cardClicked, Player player) {
			super(EventType.HandClicked);
			this.cardClicked = cardClicked;
			this.player = player;
		}
	}

	public static class ApplyButtonClickedEvent extends Event {
		public ApplyButtonClickedEvent() {
			super(EventType.ApplyButtonClicked);
		}
	}

	public static class CancelButtonClickedEvent extends Event {
		public CancelButtonClickedEvent() {
			super(EventType.CancelButtonClicked);
		}
	}

	@SuppressWarnings("unused")
	private final GUI gui;
	private final BlockingQueue<Event> eventQueue;

	public EventReceiver(GUI gui) {
		this.gui = gui;
		eventQueue = new ArrayBlockingQueue<>(8);
	}

	// TODO - lapanie eventow

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
			System.err.println("Czekanie na kolejny event zostalo przerwane:");
			e.printStackTrace();
			return null;
		}
	}

}
