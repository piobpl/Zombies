package game.view;

import game.model.Player;
import game.view.GUI.Button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import utility.Pair;

public class SimpleEventReceiver implements EventReceiver {
	private final GUI gui;
	private final BlockingQueue<Event> eventQueue;
	private final TriggerEventHandler triggerHandler;
	private final List<Filter> filters;

	public SimpleEventReceiver(GUI gui, TriggerEventHandler triggerHandler) {
		this.gui = gui;
		this.triggerHandler = triggerHandler;
		eventQueue = new ArrayBlockingQueue<>(32);
		filters = new ArrayList<>();
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
					filter(new HandClickedEvent(index, player, e));
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
								filter(new BoardClickedEvent(
										new Pair<Integer, Integer>(row, col), e));
							}
						});
			}
		}
	}

	private void registerToButtons() {
		for (Button i : Button.values()) {
			final Button button = i;
			gui.addButtonListener(button, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (button == Button.Command) {
						filter(new BossEvent());
					} else {
						filter(new ButtonClickedEvent(button));
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

	private void filter(Event event) {
		for (Filter f : filters) {
			if (!f.acceptable(event)) {
				System.err.println("BLOCKED BY FILTER!");
				return;
			}
		}
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return;
	}

}
