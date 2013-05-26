package lobby.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFileChooser;

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
					JFileChooser fileChooser = new JFileChooser();
					int result = fileChooser.showOpenDialog(null);
					byte[] save=null;
					if (result == JFileChooser.APPROVE_OPTION) {
						try {
							save = Files.readAllBytes(fileChooser.getSelectedFile().toPath());
						} catch (IOException e) {
							System.err.println("Save loading error!");
							e.printStackTrace();
						}
					}
					LocalController localController = new LocalController();
					localController.game();
					localController.gameState.load(save);
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
