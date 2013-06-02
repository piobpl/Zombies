package server.controller;

/**
 * A class storing clients' info.
 * 
 * @author krozycki
 * 
 */
public class Client {
	private String login;

	public Client(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
