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
		LOGIN, LOGOUT, CHAT, ERROR, INVITE, PLAYERLIST, GUI, INVITATIONACCEPT, GAMESTART, READYFORGAME;
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

	public static class LogoutMessage extends Message {

		private static final long serialVersionUID = -6175731736999075204L;

		public final String login;

		public LogoutMessage(String login) {
			this.login = login;
		}

		@Override
		public MessageType getType() {
			return MessageType.LOGOUT;
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

	public static class InviteMessage extends Message {

		private static final long serialVersionUID = -7977451951964451769L;

		public final String whoInvites;
		public final String whoIsInvited;

		public InviteMessage(String login, String invitedPlayer) {
			this.whoInvites = login;
			this.whoIsInvited = invitedPlayer;
		}

		@Override
		public MessageType getType() {
			return MessageType.INVITE;
		}

	}

	public static class InvitationAcceptMessage extends Message {

		private static final long serialVersionUID = -7977451951964451769L;

		public final String whoInvites;
		public final String whoIsInvited;

		public InvitationAcceptMessage(String whoInvites, String whoIsInvited) {
			this.whoInvites = whoInvites;
			this.whoIsInvited = whoIsInvited;
		}

		@Override
		public MessageType getType() {
			return MessageType.INVITATIONACCEPT;
		}

	}

	public static class GameStartMessage extends Message {

		private static final long serialVersionUID = -7977451951964451769L;

		@Override
		public MessageType getType() {
			return MessageType.GAMESTART;
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

	public static class ReadyForGameMessage extends Message {

		private static final long serialVersionUID = -3365737784561247232L;

		@Override
		public MessageType getType() {
			return MessageType.READYFORGAME;
		}

	}

}
