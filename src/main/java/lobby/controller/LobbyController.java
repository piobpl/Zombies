package lobby.controller;

import game.controller.LocalController;
import lobby.view.Lobby;
import lobby.view.LobbyEventReceiver;
import lobby.view.LobbyEventReceiver.Event;

public class LobbyController {

	public final Lobby lobby;

	public LobbyController() {
		System.err.println("Creating LobbyController...");
		lobby = new Lobby();
		System.err.println("Done");
	}

	public void lobby() {
		Event event;
		LobbyEventReceiver.ButtonClickedEvent b;
		System.err.println("Lobby started");
		while (true) {
			event = lobby.lobbyEventReceiver.getNextEvent();
			if (event.type == LobbyEventReceiver.EventType.ButtonClicked) {
				b = (LobbyEventReceiver.ButtonClickedEvent) event;
				if (b.button == Lobby.Button.NewLocalGame) {
					lobby.exit();
					new LocalController().game();
					break;
				}
			}
		}
	}

	public static void main(String args[]) {
		new LobbyController().lobby();
	}
}
