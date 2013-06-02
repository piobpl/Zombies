package server.controller;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

	@Override
	public void receive(Listener listener, Message message) {
		if (!clientsMap.containsKey(listener)) {
			listener.send(new PlayerListMessage(getClientsNames()));
			sendAll(message);
			clientsMap
					.put(listener, new Client(((LoginMessage) message).login));
		} else {
			sendAll(message);
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
