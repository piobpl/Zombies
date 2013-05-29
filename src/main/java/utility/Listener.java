package utility;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import server.controller.Message;

public class Listener implements Runnable {

	public static interface Receiver {
		public void receive(Message message);

		public void unregister(Listener listener);
	}

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	private boolean running = true;
	private Receiver receiver;
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
		this.receiver = receiver;
	}

	public synchronized void setReceiver(Receiver receiver) {
		if (this.receiver != null)
			this.receiver.unregister(this);
		this.receiver = receiver;
	}

	public synchronized void receive(Message message) {
		if (receiver != null)
			receiver.receive(message);
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
