package game.view;

import game.model.Player;
import game.view.EventReceiver.TriggerEvent;
import game.view.EventReceiver.TriggerEventHandler;
import game.view.GUIMessage.DrawCellCardMessage;
import game.view.GUIMessage.DrawCellTrapsMessage;
import game.view.GUIMessage.SetBoardColumnHighlightMessage;
import game.view.GUIMessage.SetBoardHighlightMessage;
import game.view.GUIMessage.SetBoardRowHighlightMessage;
import game.view.GUIMessage.SetCellHighlightMessage;
import game.view.GUIMessage.SetCellRedHighlightMessage;
import game.view.GUIMessage.SetHandHighlightMessage;
import game.view.GUIMessage.ToggleCellHighlightMessage;
import server.controller.Message;
import server.controller.Message.MessageType;
import utility.Listener;
import utility.Listener.Receiver;

public class GUIProxy implements Receiver, TriggerEventHandler {

	final GUI gui;
	
	public GUIProxy(Listener listener){
		gui = new SimpleGUI(this);
	}
	
	@Override
	public void receive(Listener listener, Message message) {
		if(message.getType() != MessageType.GUI)
			return;
		GUIMessage guiMessage = (GUIMessage) message;
		switch(guiMessage.getSubType()){
		case ClearBoardGlassText:
			gui.getBoard().clearGlassText();
			break;
		case DrawCellCard:
			DrawCellCardMessage drawCellCardMessage = (DrawCellCardMessage) guiMessage;
			switch(drawCellCardMessage.board){
			case 0:
				gui.getHand(Player.ZOMBIE).getCell(drawCellCardMessage.column).drawCard(drawCellCardMessage.card);
				break;
			case 1:
				gui.getBoard().getCell(drawCellCardMessage.row, drawCellCardMessage.column).drawCard(drawCellCardMessage.card);
				break;
			case 2:
				gui.getHand(Player.HUMAN).getCell(drawCellCardMessage.column).drawCard(drawCellCardMessage.card);
			}
			break;
		case DrawCellTraps:
			DrawCellTrapsMessage drawCellTrapsMessage = (DrawCellTrapsMessage) guiMessage;
			switch(drawCellTrapsMessage.board){
			case 0: 
				gui.getHand(Player.ZOMBIE).getCell(drawCellTrapsMessage.column).drawTraps(drawCellTrapsMessage.traps);
				break;
			case 1:
				gui.getBoard().getCell(drawCellTrapsMessage.row, drawCellTrapsMessage.column).drawTraps(drawCellTrapsMessage.traps);
				break;
			case 2:
				gui.getHand(Player.HUMAN).getCell(drawCellTrapsMessage.column).drawTraps(drawCellTrapsMessage.traps);
			}	
			break;
		case SetBoardColumnHighlight:
			SetBoardColumnHighlightMessage setBoardColumnHighlightMessage = (SetBoardColumnHighlightMessage) guiMessage;
			gui.getBoard().setColumnHighlight(setBoardColumnHighlightMessage.j, setBoardColumnHighlightMessage.set);
			break;
		case SetBoardHighlight:
			SetBoardHighlightMessage setBoardHighlightMessage = (SetBoardHighlightMessage) guiMessage;
			gui.getBoard().setHighlight(setBoardHighlightMessage.set);
			break;
		case SetBoardRowHighlight:
			SetBoardRowHighlightMessage setBoardRowHighlightMessage = (SetBoardRowHighlightMessage) guiMessage;
			gui.getBoard().setRowHighlight(setBoardRowHighlightMessage.i, setBoardRowHighlightMessage.set);
			break;
		case SetCellHighlight:
			SetCellHighlightMessage setCellHighlightMessage = (SetCellHighlightMessage) guiMessage;
			switch(setCellHighlightMessage.board){
			case 0:
				gui.getHand(Player.ZOMBIE).getCell(setCellHighlightMessage.column).setHighlight(setCellHighlightMessage.light);
				break;
			case 1:
				gui.getBoard().getCell(setCellHighlightMessage.row, setCellHighlightMessage.column).setHighlight(setCellHighlightMessage.light);
				break;
			case 2:
				gui.getHand(Player.HUMAN).getCell(setCellHighlightMessage.column).setHighlight(setCellHighlightMessage.light);
			}
			break;
		case SetCellRedHighlight:
			SetCellRedHighlightMessage setCellRedHighlightMessage = (SetCellRedHighlightMessage) guiMessage;
			switch(setCellRedHighlightMessage.board){
			case 0:
				gui.getHand(Player.ZOMBIE).getCell(setCellRedHighlightMessage.column).setRedHighlight(setCellRedHighlightMessage.light);
				break;
			case 1:
				gui.getBoard().getCell(setCellRedHighlightMessage.row, setCellRedHighlightMessage.column).setRedHighlight(setCellRedHighlightMessage.light);
				break;
			case 2:
				gui.getHand(Player.HUMAN).getCell(setCellRedHighlightMessage.column).setRedHighlight(setCellRedHighlightMessage.light);
			}
			break;
		case SetHandHighlight:
			SetHandHighlightMessage setHandHighlightMessage = (SetHandHighlightMessage) guiMessage;
			switch(setHandHighlightMessage.player){
			case ZOMBIE:
				gui.getHand(Player.ZOMBIE).setHighlight(setHandHighlightMessage.set);
				break;
			case HUMAN:
				gui.getHand(Player.HUMAN).setHighlight(setHandHighlightMessage.set);
			}
			break;
		case ToggleCellHighlight:
			ToggleCellHighlightMessage toggleCellHighlightMessage = (ToggleCellHighlightMessage) guiMessage;
			switch(toggleCellHighlightMessage.board){
			case 0:
				gui.getHand(Player.ZOMBIE).getCell(toggleCellHighlightMessage.column).toggleHighlight();
				break;
			case 1:
				gui.getBoard().getCell(toggleCellHighlightMessage.row, toggleCellHighlightMessage.column).toggleHighlight();
				break;
			case 2:
				gui.getHand(Player.HUMAN).getCell(toggleCellHighlightMessage.column).toggleHighlight();
			}
			break;
		}
	}

	@Override
	public void unregister(Listener listener) {
		gui.exit();
	}

	@Override
	public void receiveTriggerEvent(TriggerEvent e) {
		// TODO Auto-generated method stub
	}

}
