package utility;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import server.controller.Message;

public class Listener implements Runnable {

	public static interface Receiver {
		public void receive(Listener listener, Message message);

		public void unregister(Listener listener);
	}

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	private boolean running = true;
	private List<Receiver> receivers;
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

	public Listener(Socket socket) {
		this.socket = socket;
		receivers = new ArrayList<Receiver>();
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

	public Listener(Receiver receiver, Socket socket) {
		this(socket);
		this.receivers.add(receiver);
	}

	public synchronized void setReceiver(Receiver receiver) {
		this.receivers.add(receiver);
	}

	public synchronized void receive(Message message) {
		for(Receiver receiver: receivers)
			receiver.receive(this, message);
	}

	public void send(Message message) {
		try {
			outputBox.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (running) {
				receive((Message) in.readObject());
			}
		} catch (EOFException e) {
			System.err.println("Client disconnected");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		for(Receiver receiver: receivers)
			receiver.unregister(this);
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
	}

	public void close() {
		running = false;
	}

}
