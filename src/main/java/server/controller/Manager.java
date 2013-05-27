package server.controller;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa pomocnicza do wysyłania komunikatów graczom.
 *
 * @author michal
 *
 */
public class Manager {

	private List<Connector> clients;

	public Manager() {
		clients = new ArrayList<Connector>();
	}

	public synchronized void sendAll(Message msg) {
		for (Connector client : clients)
			client.send(msg);
	}

	// ta funkcja powinna byc "szybka" - wywoluje ja serwer, ktory nie powinien
	// czekac
	public synchronized void addClient(Socket client) {
		Connector connector = new Connector(this, client);
		clients.add(connector);
		new Thread(connector).start();
	}

	public synchronized void unregister(Connector client) {
		clients.remove(client);
	}

	public synchronized void close() {
		for (Connector client : clients)
			client.close();
	}

}
