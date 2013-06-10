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
			System.out.println("Starting server...");
			System.err.println("Starting server...");
			System.out
					.print("Creating server socket on port " + port + "...\t");
			System.err
					.print("Creating server socket on port " + port + "...\t");
			try {
				serverSocket = new ServerSocket(8888);
			} catch (IOException e) {
				System.err.println("failed, aborting.");
				e.printStackTrace(System.out);
				e.printStackTrace();
				return;
			}
			System.out.println("done.");
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
			System.out.println("Closing server...\t");
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
			System.out.println("done.");
			System.err.println("Closing server...\t");
		}
	}
}
