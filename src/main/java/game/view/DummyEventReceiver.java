package game.view;

import game.model.Player;
import game.view.GUIMessage.EventGUIMessage;
import game.view.GUIMessage.GUIMessageType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import server.controller.Message;
import utility.Listener;
import utility.Listener.Receiver;

public class DummyEventReceiver implements EventReceiver, Receiver {

	private final LinkedBlockingQueue<Event> eventQueue;
	private final TriggerEventHandler triggerHandler;
	private final Listener zombieListener;
	private final Listener humanListener;
	private final List<Filter> filters;
	private Player currentPlayer;

	public DummyEventReceiver(Listener zombieListener, Listener humanListener,
			TriggerEventHandler triggerHandler) {
		this.zombieListener = zombieListener;
		this.humanListener = humanListener;
		this.triggerHandler = triggerHandler;
		eventQueue = new LinkedBlockingQueue<>();
		filters = new ArrayList<>();
		zombieListener.addReceiver(this);
		humanListener.addReceiver(this);
	}

	public synchronized void setPlayer(Player player) {
		this.currentPlayer = player;
	}

	private boolean filter(Event event) {
		for (Filter f : filters) {
			if (!f.acceptable(event)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public synchronized void receive(Listener listener, Message message) {
		Listener current;
		if (currentPlayer == Player.ZOMBIE)
			current = zombieListener;
		else
			current = humanListener;
		if (listener != current)
			return;
		System.err.println("Przyszedl message moze event: " + message);
		if (message.getType() == Message.MessageType.GUI) {
			GUIMessage gm = (GUIMessage) message;
			if (gm.getSubType() == GUIMessageType.EventGUI) {
				EventGUIMessage egm = (EventGUIMessage) gm;
				if (filter(egm.event))
					try {
						eventQueue.put(egm.event);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}
	}

	@Override
	public void unregister(Listener listener) {
	}

	@Override
	public synchronized void addFilter(Filter filter) {
		if (!filters.contains(filter))
			filters.add(filter);
	}

	@Override
	public synchronized void removeFilter(Filter filter) {
		filters.remove(filter);
	}

	@Override
	public synchronized ClickEvent getNextClickEvent() {
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
