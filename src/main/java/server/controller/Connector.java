package server.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import server.controller.Message.ErrorMessage;
import server.controller.Message.MessageType;

public class Connector implements Runnable {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	boolean running = true;
	private Manager manager;
	LinkedBlockingQueue<Message> outputBox;

	private class Writer implements Runnable {

		@Override
		public void run() {
			outputBox = new LinkedBlockingQueue<Message>();
			while (running) {
				try {
					out.writeObject(outputBox.take());
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public Connector(Manager manager, Socket socket) {
		this.manager = manager;
		this.socket = socket;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread writer = new Thread(new Writer());
		writer.setDaemon(true);
		writer.start();
	}

	public void send(Message message) {
		try {
			outputBox.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Message msg = null;
		try {
			msg = (Message) (in.readObject());
			if (msg.getType() != MessageType.LOGIN) {
				out.writeObject(new ErrorMessage(
						"Nie rozmawiam z nieznajomymi! Dowidzenia!"));
			} else {
				manager.sendAll(msg);
				while (running) {
					msg = (Message) (in.readObject());
					if (msg.getType() == MessageType.CHAT) {
						manager.sendAll(msg);
					}
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		manager.unregister(this);
	}

	public void close() {
		running = false;
	}

}
