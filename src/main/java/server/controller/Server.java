package server.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

	public final int port = 8888;
	Manager manager;

	public static void main(String[] args) {
		try {
			System.setErr(new PrintStream(new File("Server.log")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		new Server().run();
	}

	public Server() {
		System.err.println("Creating server...");
		manager = new Manager();
		System.err.println("Done");
	}

	public void run() {
		ServerSocket serverSocket = null;
		try {
			System.err.println("Starting server...");
			System.err
					.print("Creating server socket on port " + port + "...\t");
			try {
				serverSocket = new ServerSocket(8888);
			} catch (IOException e) {
				System.err.println("failed, aborting.");
				e.printStackTrace();
				return;
			}
			System.err.println("done.");
			while (true) {
				try {
					System.err.print("Waiting for next client...\t");
					Socket client = serverSocket.accept();
					System.err.println("done.");
					manager.addClient(client);
				} catch (IOException e) {
					System.err.println("failed, aborting.");
					e.printStackTrace();
					return;
				}
			}
		} finally {
			System.err.println("Closing server...\t");
			manager.close();
			if (serverSocket != null)
				try {
					serverSocket.close();
				} catch (IOException e) {
					System.err.println("failed, aborting. (~big trouble)");
					e.printStackTrace();
					return;
				}
			System.err.println("done.");
		}
	}
}
