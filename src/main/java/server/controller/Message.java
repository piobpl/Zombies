package server.controller;

import java.io.Serializable;
import java.util.List;

/**
 * Komunikaty, które Manager będzie wysyłał do poszczególnych klientów (a serwer
 * do managera).
 * 
 * @author michal
 * 
 */

public abstract class Message implements Serializable {

	private static final long serialVersionUID = 4994144271876870878L;

	public enum MessageType {
		LOGIN, CHAT, ERROR, PLAYERLIST;
	}

	public abstract MessageType getType();

	public static class LoginMessage extends Message {

		private static final long serialVersionUID = -6175731736999075204L;

		public final String login;

		public LoginMessage(String login) {
			this.login = login;
		}

		@Override
		public MessageType getType() {
			return MessageType.LOGIN;
		}

	}

	public static class ChatMessage extends Message {

		private static final long serialVersionUID = -6175731736999075204L;

		public final String from;
		public final String message;

		public ChatMessage(String from, String message) {
			this.from = from;
			this.message = message;
		}

		@Override
		public MessageType getType() {
			return MessageType.CHAT;
		}

	}

	public static class ErrorMessage extends Message {

		private static final long serialVersionUID = -6175731736999075204L;

		public final String message;

		public ErrorMessage(String message) {
			this.message = message;
		}

		@Override
		public MessageType getType() {
			return MessageType.ERROR;
		}

	}

	public static class PlayerListMessage extends Message {

		private static final long serialVersionUID = 8002938395596238582L;

		public final List<String> playerList;

		public PlayerListMessage(List<String> playerList) {
			this.playerList = playerList;
		}

		@Override
		public MessageType getType() {
			return MessageType.PLAYERLIST;
		}

	}

}
