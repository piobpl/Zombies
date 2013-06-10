package game.view;

import game.model.Player;
import game.view.EventReceiver.Event;
import game.view.EventReceiver.TriggerEvent;
import game.view.EventReceiver.TriggerEventHandler;
import game.view.GUIMessage.DrawCellCardMessage;
import game.view.GUIMessage.DrawCellTrapsMessage;
import game.view.GUIMessage.DrawGUIGlobalModifiersMessage;
import game.view.GUIMessage.EventGUIMessage;
import game.view.GUIMessage.ModelGUISendsMessage;
import game.view.GUIMessage.SetBoardColumnHighlightMessage;
import game.view.GUIMessage.SetBoardHighlightMessage;
import game.view.GUIMessage.SetBoardRowHighlightMessage;
import game.view.GUIMessage.SetCellGlassTextMessage;
import game.view.GUIMessage.SetCellHighlightMessage;
import game.view.GUIMessage.SetCellRedHighlightMessage;
import game.view.GUIMessage.SetGUIButtonEnabledMessage;
import game.view.GUIMessage.SetGUICardsLeftMessage;
import game.view.GUIMessage.SetGUIHighlightMessage;
import game.view.GUIMessage.SetHandHighlightMessage;
import game.view.GUIMessage.ToggleCellHighlightMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import server.controller.Message;
import server.controller.Message.ChatMessage;
import server.controller.Message.MessageType;
import server.controller.Message.ReadyForGameMessage;
import utility.Listener;
import utility.Listener.Receiver;

public class GUIProxy implements Receiver, TriggerEventHandler, Runnable,
		ChatProxy {

	final String login;
	final Listener listener;
	final SimpleGUI gui;

	public GUIProxy(Listener listener, String login) {
		try {
			System.setErr(new PrintStream(new File("GUIProxy.log")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.err.println("Proxy gracza: " + login);
		gui = new SimpleGUI(this, this);
		gui.hideHistoryPanel();
		this.listener = listener;
		this.login = login;
		listener.addReceiver(this);
		listener.sendAndWait(new ReadyForGameMessage());
	}

	@Override
	public synchronized void receive(Listener listener, Message message) {
		if (message.getType() == MessageType.CHAT) {
			ChatMessage cm = (ChatMessage) message;
			gui.modelSendsMessage(cm.from + " writes: " + cm.message);
		}
		if (message.getType() != MessageType.GUI)
			return;
		GUIMessage guiMessage = (GUIMessage) message;
		switch (guiMessage.getSubType()) {
		case ClearBoardGlassText:
			gui.getBoard().clearGlassText();
			break;
		case DrawCellCard:
			DrawCellCardMessage drawCellCardMessage = (DrawCellCardMessage) guiMessage;
			switch (drawCellCardMessage.board) {
			case 0:
				gui.getHand(Player.ZOMBIE).getCell(drawCellCardMessage.column)
						.drawCard(drawCellCardMessage.card);
				break;
			case 1:
				gui.getBoard()
						.getCell(drawCellCardMessage.row,
								drawCellCardMessage.column)
						.drawCard(drawCellCardMessage.card);
				break;
			case 2:
				gui.getHand(Player.HUMAN).getCell(drawCellCardMessage.column)
						.drawCard(drawCellCardMessage.card);
			}
			break;
		case DrawCellTraps:
			DrawCellTrapsMessage drawCellTrapsMessage = (DrawCellTrapsMessage) guiMessage;
			switch (drawCellTrapsMessage.board) {
			case 0:
				gui.getHand(Player.ZOMBIE).getCell(drawCellTrapsMessage.column)
						.drawTraps(drawCellTrapsMessage.traps);
				break;
			case 1:
				gui.getBoard()
						.getCell(drawCellTrapsMessage.row,
								drawCellTrapsMessage.column)
						.drawTraps(drawCellTrapsMessage.traps);
				break;
			case 2:
				gui.getHand(Player.HUMAN).getCell(drawCellTrapsMessage.column)
						.drawTraps(drawCellTrapsMessage.traps);
			}
			break;
		case SetBoardColumnHighlight:
			SetBoardColumnHighlightMessage setBoardColumnHighlightMessage = (SetBoardColumnHighlightMessage) guiMessage;
			gui.getBoard().setColumnHighlight(setBoardColumnHighlightMessage.j,
					setBoardColumnHighlightMessage.set);
			break;
		case SetBoardHighlight:
			SetBoardHighlightMessage setBoardHighlightMessage = (SetBoardHighlightMessage) guiMessage;
			gui.getBoard().setHighlight(setBoardHighlightMessage.set);
			break;
		case SetBoardRowHighlight:
			SetBoardRowHighlightMessage setBoardRowHighlightMessage = (SetBoardRowHighlightMessage) guiMessage;
			gui.getBoard().setRowHighlight(setBoardRowHighlightMessage.i,
					setBoardRowHighlightMessage.set);
			break;
		case SetCellHighlight:
			SetCellHighlightMessage setCellHighlightMessage = (SetCellHighlightMessage) guiMessage;
			switch (setCellHighlightMessage.board) {
			case 0:
				gui.getHand(Player.ZOMBIE)
						.getCell(setCellHighlightMessage.column)
						.setHighlight(setCellHighlightMessage.light);
				break;
			case 1:
				gui.getBoard()
						.getCell(setCellHighlightMessage.row,
								setCellHighlightMessage.column)
						.setHighlight(setCellHighlightMessage.light);
				break;
			case 2:
				gui.getHand(Player.HUMAN)
						.getCell(setCellHighlightMessage.column)
						.setHighlight(setCellHighlightMessage.light);
			}
			break;
		case SetCellGlassText:
			SetCellGlassTextMessage setCellGlassTextMessage = (SetCellGlassTextMessage) guiMessage;
			switch (setCellGlassTextMessage.board) {
			case 0:
				gui.getHand(Player.ZOMBIE)
						.getCell(setCellGlassTextMessage.column)
						.setGlassText(setCellGlassTextMessage.text);
				break;
			case 1:
				gui.getBoard()
						.getCell(setCellGlassTextMessage.row,
								setCellGlassTextMessage.column)
						.setGlassText(setCellGlassTextMessage.text);
				break;
			case 2:
				gui.getHand(Player.HUMAN)
						.getCell(setCellGlassTextMessage.column)
						.setGlassText(setCellGlassTextMessage.text);
			}
			break;
		case SetCellRedHighlight:
			SetCellRedHighlightMessage setCellRedHighlightMessage = (SetCellRedHighlightMessage) guiMessage;
			switch (setCellRedHighlightMessage.board) {
			case 0:
				gui.getHand(Player.ZOMBIE)
						.getCell(setCellRedHighlightMessage.column)
						.setRedHighlight(setCellRedHighlightMessage.light);
				break;
			case 1:
				gui.getBoard()
						.getCell(setCellRedHighlightMessage.row,
								setCellRedHighlightMessage.column)
						.setRedHighlight(setCellRedHighlightMessage.light);
				break;
			case 2:
				gui.getHand(Player.HUMAN)
						.getCell(setCellRedHighlightMessage.column)
						.setRedHighlight(setCellRedHighlightMessage.light);
			}
			break;
		case SetHandHighlight:
			SetHandHighlightMessage setHandHighlightMessage = (SetHandHighlightMessage) guiMessage;
			switch (setHandHighlightMessage.player) {
			case ZOMBIE:
				gui.getHand(Player.ZOMBIE).setHighlight(
						setHandHighlightMessage.set);
				break;
			case HUMAN:
				gui.getHand(Player.HUMAN).setHighlight(
						setHandHighlightMessage.set);
			}
			break;
		case ToggleCellHighlight:
			ToggleCellHighlightMessage toggleCellHighlightMessage = (ToggleCellHighlightMessage) guiMessage;
			switch (toggleCellHighlightMessage.board) {
			case 0:
				gui.getHand(Player.ZOMBIE)
						.getCell(toggleCellHighlightMessage.column)
						.toggleHighlight();
				break;
			case 1:
				gui.getBoard()
						.getCell(toggleCellHighlightMessage.row,
								toggleCellHighlightMessage.column)
						.toggleHighlight();
				break;
			case 2:
				gui.getHand(Player.HUMAN)
						.getCell(toggleCellHighlightMessage.column)
						.toggleHighlight();
			}
			break;
		case DrawGlobalModifiers:
			DrawGUIGlobalModifiersMessage drawGUIGlobalModifiersMessage = (DrawGUIGlobalModifiersMessage) guiMessage;
			gui.drawGlobalModifiers(drawGUIGlobalModifiersMessage.modifiers);
			break;
		case ExitGUI:
			gui.exit();
			break;
		case ModelGUISends:
			ModelGUISendsMessage modelGUISendsMessage = (ModelGUISendsMessage) guiMessage;
			gui.modelSendsMessage(modelGUISendsMessage.message);
			break;
		case SetGUIButtonEnabled:
			SetGUIButtonEnabledMessage setGUIButtonEnabledMessage = (SetGUIButtonEnabledMessage) guiMessage;
			gui.setButtonEnabled(setGUIButtonEnabledMessage.button,
					setGUIButtonEnabledMessage.aktywny);
			break;
		case SetGUICardsLeft:
			SetGUICardsLeftMessage setGUICardsLeftMessage = (SetGUICardsLeftMessage) guiMessage;
			gui.setCardsLeft(setGUICardsLeftMessage.player,
					setGUICardsLeftMessage.left);
			break;
		case SetGUIHighlight:
			SetGUIHighlightMessage setGUIHighlightMessage = (SetGUIHighlightMessage) guiMessage;
			gui.setHighlight(setGUIHighlightMessage.set);
			break;
		case EventGUI:
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void unregister(Listener listener) {
		gui.exit();
	}

	@Override
	public synchronized void receiveTriggerEvent(TriggerEvent e) {
		listener.sendAndWait(new EventGUIMessage(e));
	}

	@Override
	public void run() {
		while (true) {
			Event e = gui.getEventReceiver().getNextClickEvent();
			listener.sendAndWait(new EventGUIMessage(e));
		}
	}

	@Override
	public synchronized void send(String message) {
		System.err.println("Przesylamy chat message w gore: " + message);
		listener.sendAndWait(new ChatMessage(login, message));
	}
}
