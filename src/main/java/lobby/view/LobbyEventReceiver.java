package lobby.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import lobby.view.Lobby.Button;

public class LobbyEventReceiver {

	public static enum EventType {
		ButtonClicked;
	}

	public abstract static class Event {
		public final EventType type;
		public final MouseEvent info;

		public Event(EventType type, MouseEvent info) {
			this.type = type;
			this.info = info;
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

	private final Lobby lobby;
	private final BlockingQueue<Event> eventQueue;

	public LobbyEventReceiver(Lobby lobby) {
		this.lobby = lobby;
		eventQueue = new ArrayBlockingQueue<>(32);
		registerToButtons();
	}

	private void registerToButtons() {
		for (Lobby.Button i : Lobby.Button.values()) {
			final Lobby.Button button = i;
			lobby.addButtonMouseListener(button, new SimpleMouseListener() {
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

	public Event getNextEvent() {
		try {
			return eventQueue.take();
		} catch (InterruptedException e) {
			System.err.println("Waiting for next event interrupted");
			e.printStackTrace();
			return null;
		}
	}

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
