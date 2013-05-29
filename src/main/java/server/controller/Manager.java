package server.controller;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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

	public Manager() {
		clients = new ArrayList<Listener>();
	}

	@Override
	public void receive(Message message) {
		sendAll(message);
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

	public synchronized void unregister(Listener connector) {
		clients.remove(connector);
	}

	public synchronized void close() {
		for (Listener connector : clients)
			connector.close();
	}

}
