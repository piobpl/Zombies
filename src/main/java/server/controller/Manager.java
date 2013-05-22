package server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

// TODO
public class Manager implements Runnable {

	public Manager() {

	}

	// ta funkcja powinna byc "szybka" - wywoluje ja serwer, ktory nie powinien
	// czekac
	public void addClient(Socket client) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		printWriter.print("No, witam!");
		printWriter.close();
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

	}

}
