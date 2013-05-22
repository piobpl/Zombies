package server.controller;

public class Server {

	public static void main(String[] args) {
		new Server().run();
	}

	public Server(){
		System.out.println("Creating server...");
		System.out.println("Done");
	}

	public void run() {
		System.out.println("Starting server...");
		System.out.println("Closing server...");
	}

}
