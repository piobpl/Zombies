package game.view;

import game.controller.Selection;
import game.controller.Selector;
import game.model.GameState;
import game.model.Player;
import game.model.cards.zombies.BossMover;
import game.view.GUI.Button;

import java.awt.event.MouseEvent;
import java.io.Serializable;

import utility.Pair;

/**
 * Klasa służaca do komunikacji z użytkownikiem.
 *
 * @author piob
 */
public interface EventReceiver {

	public interface Filter {
		public boolean acceptable(Event event);
	}

	public void addFilter(Filter filter);

	public void removeFilter(Filter filter);

	public static enum EventType {
		BoardClicked, HandClicked, ButtonClicked, Trigger;
	}

	public abstract static class Event implements Serializable {
		private static final long serialVersionUID = 4132830855233184395L;
		public final EventType type;

		public Event(EventType type) {
			this.type = type;
		}
	}

	public abstract static class ClickEvent extends Event {
		private static final long serialVersionUID = 4132830855233184395L;
		public ClickEvent(EventType type) {
			super(type);
		}
	}

	public abstract static class MouseClickEvent extends ClickEvent {
		private static final long serialVersionUID = 4132830855233184395L;
		public final MouseEvent info;
		public MouseClickEvent(EventType type, MouseEvent info) {
			super(type);
			this.info = info;
		}
	}

	public static class BoardClickedEvent extends MouseClickEvent {
		private static final long serialVersionUID = 4132830855233184395L;
		public final Pair<Integer, Integer> cardClicked;

		public BoardClickedEvent(Pair<Integer, Integer> cardClicked,
				MouseEvent info) {
			super(EventType.BoardClicked, info);
			this.cardClicked = cardClicked;
		}
	}

	public static class HandClickedEvent extends MouseClickEvent {
		private static final long serialVersionUID = 4132830855233184395L;
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
		private static final long serialVersionUID = 4132830855233184395L;
		public final Button button;

		public ButtonClickedEvent(Button button) {
			super(EventType.ButtonClicked);
			this.button = button;
		}
	}

	public abstract static class TriggerEvent extends Event {
		private static final long serialVersionUID = 4132830855233184395L;

		public TriggerEvent() {
			super(EventType.Trigger);
		}

		public abstract void trigger(GameState gameState);

	}

	public static class BossEvent extends TriggerEvent {
		private static final long serialVersionUID = 4132830855233184395L;

		@Override
		public void trigger(GameState gameState) {
			gameState.gui.setButtonEnabled(Button.Command, false);
			Selector selector = new Selector(gameState, gameState.gui);
			BossMover mover = new BossMover();
			Selection selection = selector.getSelection(mover);
			if (selection != null)
				mover.makeEffect(selection, gameState);
			else
				gameState.gui.setButtonEnabled(Button.Command, true);
			gameState.gui.setHighlight(false);
		}

	}

	public static interface TriggerEventHandler {
		public void receiveTriggerEvent(TriggerEvent e);
	}

	/**
	 * Funkcja zwraca kolejną akcje wykonaną przez użytkownika. Jeżeli
	 * użytkownik nie podjął żadnego działania, blokuje wątek w oczekiwaniu.
	 * Jeżeli zostanie przerwana, wypisuje o tym informacje na stderr i zwraca
	 * null.
	 */
	public ClickEvent getNextClickEvent();

}
