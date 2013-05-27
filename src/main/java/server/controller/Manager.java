package server.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
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
	private List<ObjectOutputStream> outputStreams;

	public Manager() {

	}

	public void sendAll(Message msg) {
		for (ObjectOutputStream stream : outputStreams)
			try {
				stream.writeObject(msg);
			} catch (IOException e) {
				System.err.println("Nie moge pisac do strumienia " + stream);
				e.printStackTrace();
			}
	}

	// ta funkcja powinna byc "szybka" - wywoluje ja serwer, ktory nie powinien
	// czekac
	public void addClient(final Socket client, SomeoneHasLoggedIn msg) {
		sendAll(msg);
		clients.add(client);
		try {
			outputStreams.add(new ObjectOutputStream(client.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread listener = new Thread(new Runnable() {
				private ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
				@Override
				public void run() {
					Message msg = null;
					while(true) {
						try {
							msg = (Message) (inputStream.readObject());
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sendAll(msg);
					}
				}
			});
			listener.setDaemon(true);
			listener.start();
		} catch (IOException e) {
			System.err.println("Nie moge utworzyc watku do nasluchiwania!");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
	}

}
