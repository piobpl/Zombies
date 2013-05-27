package server.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import server.messages.SomeoneHasLoggedIn;

/**
 * Klasa pomocnicza do wysyłania komunikatów graczom.
 * 
 * @author michal
 *
 */
public class Manager implements Runnable {
	
	private List<Socket> clients = new LinkedList<Socket>();
	private List<ObjectOutputStream> streams;

	public Manager() {
		
	}

	// ta funkcja powinna byc "szybka" - wywoluje ja serwer, ktory nie powinien
	// czekac
	public void addClient(Socket client, SomeoneHasLoggedIn msg) {
		for (ObjectOutputStream stream : streams)
			try {
				stream.writeObject(msg);
			} catch (IOException e) {
				System.err.println("Nie moge pisac do strumienia " + stream);
				e.printStackTrace();
			}
		clients.add(client);
		try {
			streams.add(new ObjectOutputStream(client.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

	}

}
