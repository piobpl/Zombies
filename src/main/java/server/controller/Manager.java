package server.controller;

import game.controller.NetController;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import server.controller.Message.ErrorMessage;
import server.controller.Message.GameStartMessage;
import server.controller.Message.InviteMessage;
import server.controller.Message.LoginMessage;
import server.controller.Message.LogoutMessage;
import server.controller.Message.PlayerListMessage;
import utility.Listener;
import utility.Listener.Receiver;

/**
 * Klasa pomocnicza do wysyłania komunikatów graczom.
 * 
 * @author michal
 * 
 */
public class Manager implements Receiver {

	private List<Listener> clients;
	private Map<Listener, Client> clientsMap;

	public Manager() {
		clients = new ArrayList<Listener>();
		clientsMap = new HashMap<Listener, Client>();
	}

	private boolean loginTaken(String login) {
		for (Listener connector : clients) {
			if (clientsMap.get(connector) != null
					&& clientsMap.get(connector).getLogin().equals(login)) {
				return true;
			}
		}
		return false;
	}

	public synchronized Listener getListener(String login) {
		for (Listener connector : clients)
			if (clientsMap.get(connector) != null
					&& clientsMap.get(connector).getLogin().equals(login))
				return connector;
		return null;
	}

	public synchronized void sendToPlayer(String name, Message message) {
		System.err.println("Sending to " + name + ":");
		System.err.println(message);
		getListener(name).send(message);
	}

	@Override
	public synchronized void receive(Listener listener, Message message) {
		if (!clientsMap.containsKey(listener)) {
			if (message.getType() != Message.MessageType.LOGIN)
				return;
			LoginMessage m = ((LoginMessage) message);
			if (loginTaken(m.login)) {
				listener.send(new ErrorMessage("This login is already taken."));
				return;
			}
			listener.send(new PlayerListMessage(getClientsNames()));
			sendAll(message);
			clientsMap
					.put(listener, new Client(((LoginMessage) message).login));
		} else {
			if (message.getType() == Message.MessageType.INVITE) {
				sendToPlayer(((InviteMessage) message).whoIsInvited, message);
			} else if (message.getType() == Message.MessageType.GAMESTART) {
				GameStartMessage gsm = (GameStartMessage) message;
				sendToPlayer(gsm.whoInvites, message);
				Random random = new Random();
				if (random.nextBoolean())
					new Thread(new NetController(getListener(gsm.whoInvites),
							getListener(gsm.whoIsInvited))).start();
				else
					new Thread(new NetController(getListener(gsm.whoIsInvited),
							getListener(gsm.whoInvites))).start();
				Listener client = getListener(gsm.whoInvites);
				client.removeReceiver(this);
				clients.remove(client);
				clientsMap.remove(client);
				client = getListener(gsm.whoIsInvited);
				client.removeReceiver(this);
				clients.remove(client);
				clientsMap.remove(client);
			} else {
				sendAll(message);
			}
		}
	}

	public synchronized void sendAll(Message message) {
		System.err.println("Sending to all:");
		System.err.println(message);
		for (Listener connector : clients)
			connector.send(message);
	}

	// ta funkcja powinna byc "szybka" - wywoluje ja serwer, ktory nie powinien
	// czekac
	public synchronized void addClient(Socket client) {
		Listener connector = new Listener(this, client);
		clients.add(connector);
		new Thread(connector).start();
	}

	public List<String> getClientsNames() {
		List<String> L = new LinkedList<>();
		for (Client tmp : clientsMap.values()) {
			L.add(tmp.getLogin());
		}
		return L;
	}

	public synchronized void unregister(Listener connector) {
		sendAll(new LogoutMessage(clientsMap.get(connector).getLogin()));
		clients.remove(connector);
		clientsMap.remove(connector);
	}

	public synchronized void close() {
		for (Listener connector : clients)
			connector.close();
	}

}
