package utility;

import java.io.ByteArrayOutputStream;
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
	private boolean isPaused;

	private class Writer implements Runnable {
		@Override
		public void run() {
			while (running) {
				try {
					Object o = outputBox.take();
					System.err.println("Wysylamy: " + o);
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

	public void addReceiver(Receiver receiver) {
		this.receivers.add(receiver);
	}

	public void removeReceiver(Receiver receiver) {
		this.receivers.remove(receiver);
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

	public synchronized void pause() {
		isPaused = true;
		System.err.println("pauza = " + isPaused);
	}

	public synchronized void play() {
		isPaused = false;
		System.err.println("pauza = " + isPaused);
		notifyAll();
	}

	public void run() {
		try {
			while (running) {
				Message msg = (Message) in.readObject();
				System.err.println("Dostalem: " + msg);
				synchronized (this) {
					while (isPaused)
						wait();
				}
				System.err.println("Odbieram: " + msg);
				receive(msg);
			}
		} catch (EOFException e) {
			System.err.println("Client disconnected");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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

}
