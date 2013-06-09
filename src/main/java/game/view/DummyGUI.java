package game.view;

import game.model.Modifier;
import game.model.Player;
import game.view.EventReceiver.TriggerEventHandler;
import game.view.GUIMessage.DrawGUIGlobalModifiersMessage;
import game.view.GUIMessage.ExitGUIMessage;
import game.view.GUIMessage.ModelGUISendsMessage;
import game.view.GUIMessage.SetGUIButtonEnabledMessage;
import game.view.GUIMessage.SetGUICardsLeftMessage;
import game.view.GUIMessage.SetGUIHighlightMessage;

import java.awt.event.ActionListener;
import java.util.EnumSet;
import java.util.List;

import javax.swing.event.ChangeListener;

import server.controller.Message;
import server.controller.Message.GameStartMessage;
import server.controller.Message.MessageType;
import utility.Listener;
import utility.Listener.Receiver;

public class DummyGUI implements GUI, Receiver {

	Listener zombieListener, humanListener;
	DummyEventReceiver dummyEventReceiver;
	boolean zombiePlayerReady, humanPlayerReady;
	EnumSet<Button> activeButtons;
	Player currentPlayer;
	String zombieNick, humanNick;

	public DummyGUI(Listener zombieListener, Listener humanListener,
			TriggerEventHandler triggerEventHandler, String zombieNick,
			String humanNick) {
		System.err.println("Dummy gui " + zombieListener + " " + humanListener);
		this.zombieListener = zombieListener;
		this.humanListener = humanListener;
		zombieListener.addReceiver(this);
		humanListener.addReceiver(this);
		dummyEventReceiver = new DummyEventReceiver(zombieListener,
				humanListener, triggerEventHandler);
		zombiePlayerReady = humanPlayerReady = false;
		activeButtons = EnumSet.noneOf(Button.class);
		currentPlayer = Player.ZOMBIE;
		this.zombieNick = zombieNick;
		this.humanNick = humanNick;
	}

	public synchronized void waitTillPlayersAreReady() {
		System.err.println("czekam na graczy " + zombieListener + " "
				+ humanListener);
		zombieListener.send(new GameStartMessage());
		while (!zombiePlayerReady)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		humanListener.send(new GameStartMessage());
		while (!humanPlayerReady)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.err.println("gotowe");
	}

	@Override
	public synchronized void setPlayer(Player player) {
		dummyEventReceiver.setPlayer(player);
		currentPlayer = player;
		for (Button button : Button.values()) {
			if (currentPlayer == Player.ZOMBIE) {
				if (activeButtons.contains(button))
					zombieListener.send(new SetGUIButtonEnabledMessage(button,
							true));
				else
					zombieListener.send(new SetGUIButtonEnabledMessage(button,
							false));
				humanListener
						.send(new SetGUIButtonEnabledMessage(button, false));
			} else {
				if (activeButtons.contains(button))
					humanListener.send(new SetGUIButtonEnabledMessage(button,
							true));
				else
					humanListener.send(new SetGUIButtonEnabledMessage(button,
							false));
				zombieListener.send(new SetGUIButtonEnabledMessage(button,
						false));
			}
		}
		if (currentPlayer == Player.ZOMBIE) {
			modelSendsMessage(zombieNick + " is playing now.");
		} else {
			modelSendsMessage(humanNick + " is playing now.");
		}
	}

	@Override
	public synchronized void setButtonEnabled(Button button, boolean aktywny) {
		if (currentPlayer == Player.ZOMBIE)
			zombieListener
					.send(new SetGUIButtonEnabledMessage(button, aktywny));
		else
			humanListener.send(new SetGUIButtonEnabledMessage(button, aktywny));
	}

	@Override
	public synchronized boolean isButtonEnabled(Button button) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized Hand getHand(Player player) {
		return new DummyHand(zombieListener, humanListener, player);
	}

	@Override
	public synchronized Board getBoard() {
		return new DummyBoard(zombieListener, humanListener);
	}

	@Override
	public synchronized HistoryPanel getHistory() {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized EventReceiver getEventReceiver() {
		return dummyEventReceiver;
	}

	@Override
	public synchronized void addButtonListener(Button button, ActionListener a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized void addSliderChangeListener(ChangeListener cl) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized void setCardsLeft(Player player, int left) {
		zombieListener.send(new SetGUICardsLeftMessage(player, left));
		humanListener.send(new SetGUICardsLeftMessage(player, left));
	}

	@Override
	public synchronized void modelSendsMessage(String message) {
		zombieListener.send(new ModelGUISendsMessage(message));
		humanListener.send(new ModelGUISendsMessage(message));
	}

	@Override
	public synchronized void modelSendsAllMessages(List<String> messages) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized void modelSetsAllMessages(List<String> messages) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized void drawGlobalModifiers(List<Modifier> modifiers) {
		zombieListener.send(new DrawGUIGlobalModifiersMessage(modifiers));
		humanListener.send(new DrawGUIGlobalModifiersMessage(modifiers));
	}

	@Override
	public synchronized void drawHistorySlider(int turn) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized void setHighlight(boolean set) {
		zombieListener.send(new SetGUIHighlightMessage(set));
		humanListener.send(new SetGUIHighlightMessage(set));
	}

	@Override
	public synchronized void exit() {
		zombieListener.send(new ExitGUIMessage());
		humanListener.send(new ExitGUIMessage());
	}

	@Override
	public synchronized void receive(Listener listener, Message message) {
		if (message.getType() == MessageType.READYFORGAME) {
			if (listener == zombieListener)
				zombiePlayerReady = true;
			if (listener == humanListener)
				humanPlayerReady = true;
			System.err.println("gotowi? " + zombiePlayerReady + " "
					+ humanPlayerReady);
			notifyAll();
		} else if (message.getType() == MessageType.CHAT) {
			System.err.println("Rozsylamy chat message: " + message);
			zombieListener.send(message);
			humanListener.send(message);
		}
	}

	@Override
	public void unregister(Listener listener) {
		modelSendsMessage("Your opponent has disconnected.");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		exit();
	}
}
