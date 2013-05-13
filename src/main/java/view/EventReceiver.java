package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

	private static class SimpleMouseListener implements MouseListener {

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
	
	private void registerToHand(final Player player){
		for (int i = 0; i < 4; ++i) {
			final int index = i;
			gui.getHand(player).getCell(i)
					.addMouseListener(new SimpleMouseListener() {
						@Override
						public void mouseClicked(MouseEvent e) {
							try {
								eventQueue.put(new HandClickedEvent(index,
										player));
								System.err.println("Clicked: " + index + " on " + player + " hand");
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
		}
	}
	
	private void registerToBoard(){
		for(int i = 0; i < 5; i++){
			final int row = i;
			for(int j = 0; j < 3; j++){
				final int col = j;
				gui.getBoard().getCell(row, col).addMouseListener(new SimpleMouseListener(){
					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							eventQueue.put(new BoardClickedEvent(new Pair<Integer, Integer>(row, col)));
							System.err.println("Clicked board at (" + row + ", " + col + ")");
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		}
	}

	private void registerToButtons(){
		gui.addApplyButtonMouseListener(new SimpleMouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					eventQueue.put(new ApplyButtonClickedEvent());
					System.err.println("applyButton clicked");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		gui.addCancelButtonMouseListener(new SimpleMouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					eventQueue.put(new CancelButtonClickedEvent());
					System.err.println("cancelButton clicked");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
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

}
