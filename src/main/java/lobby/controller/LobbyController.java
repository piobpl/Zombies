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
					System.err.println("Starting new local game");
					lobby.exit();
					new LocalController().game();
					break;
				}else if(b.button == Lobby.Button.LoadLocalGame) {
					System.err.println("Loading saved local game");
					lobby.exit();
					//TODO wybieranie pliku save'a z dysku ?
					break;
				}else if(b.button == Lobby.Button.NewNetworkGame){
					System.err.println("Starting new network game");
					lobby.exit();
					//TODO
					break;					
				}else if(b.button == Lobby.Button.LoadNetworkGame){
					System.err.println("Loading saved network game");
					lobby.exit();
					//TODO
					break;
				}
			}
		}
	}

	public static void main(String args[]) {
		new LobbyController().lobby();
	}
}
