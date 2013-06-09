package utility;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
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
	private CopyOnWriteArrayList<Receiver> receivers;
	private LinkedBlockingQueue<Message> outputBox;

	private class Writer implements Runnable {
		@Override
		public void run() {
			while (running) {
				try {
					Object o = outputBox.take();
					out.writeObject(o);
					out.flush();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public Listener(Socket socket) {
		this.socket = socket;
		receivers = new CopyOnWriteArrayList<Receiver>();
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
		receivers.add(receiver);
	}

	public void addReceiver(Receiver receiver) {
		receivers.add(receiver);
	}

	public void removeReceiver(Receiver receiver) {
		receivers.remove(receiver);
	}

	public void receive(Message message) {
		for (Receiver receiver : receivers)
			receiver.receive(this, message);
	}

	public void send(Message message) {
		try {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bytes);
			out.writeObject(message);
			outputBox.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (running) {
				Message msg = (Message) in.readObject();
				receive(msg);
			}
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		System.err.println("!!!!!!!!Client disconnected!!!!!!!!");
		for (Receiver receiver : receivers)
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

	public void finalize() {
		System.err.println("KTOS NAM KASUJE LISTENER");
	}

}
