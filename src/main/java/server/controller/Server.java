package server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.messages.SomeoneHasLoggedIn;

public class Server implements Runnable {

	public final int port = 8888;
	Manager manager;

	public static void main(String[] args) {
		new Server().run();
	}

	public Server() {
		System.out.println("Creating server...");
		manager = new Manager();
		System.out.println("Done");
	}

	public void run() {
		ServerSocket serverSocket = null;
		try {
			System.out.println("Starting server...");
			System.out
					.print("Creating server socket on port " + port + "...\t");
			try {
				serverSocket = new ServerSocket(8888);
			} catch (IOException e) {
				System.out.println("failed, aborting.");
				e.printStackTrace();
				return;
			}
			System.out.println("done.");
			try {
				System.out.print("Waiting for next client...\t");
				Socket client = serverSocket.accept();
				System.out.println("done.");
				manager.addClient(client, new SomeoneHasLoggedIn());
			} catch (IOException e) {
				System.out.println("failed, aborting.");
				e.printStackTrace();
				return;
			}
		} finally {
			System.out.println("Closing server...\t");
			if (serverSocket != null)
				try {
					serverSocket.close();
				} catch (IOException e) {
					System.out.println("failed, aborting. (~big trouble)");
					e.printStackTrace();
					return;
				}
			System.out.println("done.");
		}
	}
}
