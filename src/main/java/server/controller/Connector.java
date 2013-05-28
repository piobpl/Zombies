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
	private boolean running = true;
	private Manager manager;
	private LinkedBlockingQueue<Message> outputBox;

	private class Writer implements Runnable {

		@Override
		public void run() {
			while (running) {
				try {
					out.writeObject(outputBox.take());
					out.flush();
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
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		outputBox = new LinkedBlockingQueue<Message>();
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
				send(new ErrorMessage(
						"Nie rozmawiam z nieznajomymi! Dowidzenia!"));
				Thread.yield();
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
