package game.view;

import game.model.Player;
import game.view.GUI.Button;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import utility.Pair;

public class SimpleEventReceiver implements EventReceiver {
	private final GUI gui;
	private final BlockingQueue<Event> eventQueue;
	private final TriggerEventHandler triggerHandler;

	public SimpleEventReceiver(GUI gui, TriggerEventHandler triggerHandler) {
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
						if (filter(e)) {
							eventQueue.put(new HandClickedEvent(index, player,
									e));
							System.err.println("" + e.getButton()
									+ " clicked: " + index + " on " + player
									+ " hand");
						} else {
							System.err.println("" + e.getButton()
									+ " clicked: " + index + " on " + player
									+ " hand" + " BLOCKED BY FILTER!");
						}
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
									if (filter(e)) {
										eventQueue.put(new BoardClickedEvent(
												new Pair<Integer, Integer>(row,
														col), e));
										System.err.println("" + e.getButton()
												+ " clicked board at (" + row
												+ ", " + col + ")");
									} else {
										eventQueue.put(new BoardClickedEvent(
												new Pair<Integer, Integer>(row,
														col), e));
										System.err.println("" + e.getButton()
												+ " clicked board at (" + row
												+ ", " + col + ")"
												+ " BLOCKED BY FILTER!");
									}
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
						if (filter(e)) {
							if (button == Button.Command)
								eventQueue.put(new BossEvent());
							else
								eventQueue
										.put(new ButtonClickedEvent(button, e));
							System.err.println("" + e.getButton() + " "
									+ button + " clicked");
						} else {
							System.err.println("" + e.getButton() + " "
									+ button + " clicked"
									+ " BLOCKED BY FILTER!");
						}
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

	@Override
	public void addFilter(Filter filter) {
		if(!filters.contains(filter))
			filters.add(filter);
	}

	@Override
	public void removeFilter(Filter filter) {
		filters.remove(filter);
		for(Filter f: filters){
			System.err.print(f+" ");
		}
	}

	@Override
	public boolean filter(MouseEvent event) {
		for (Filter f : filters) {
			if (!f.acceptable(event)) {
				return false;
			}
		}
		return true;
	}

}
