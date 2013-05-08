package view;

import model.Player;
import utility.Pair;

public class EventReceiver {
		
	public static enum EventType {
		BoardClicked, HandClicked, ApplyButtonClicked, CancelButtonClicked,
	}

	public abstract static class Event {
		public EventType type;

		public Event(EventType type) {
			this.type = type;
		}
	}

	public static class BoardClickedEvent extends Event {
		public BoardClickedEvent() {
			super(EventType.BoardClicked);
		}

		Pair<Integer, Integer> cardClicked;
	}

	public static class HandClickedEvent extends Event {
		public HandClickedEvent() {
			super(EventType.HandClicked);
		}

		Integer cardClicked;
		Player player;
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

	public final GUI gui;
	
	public EventReceiver(GUI gui){
		this.gui = gui;
	}
	
	// TODO

	public Event getNextEvent() {
		return null;
	}

}
