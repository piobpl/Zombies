package game.view;

import game.model.Modifier;
import game.model.Player;
import game.view.EventReceiver.TriggerEventHandler;
import game.view.GUIMessage.DrawGUIGlobalModifiersMessage;
import game.view.GUIMessage.ExitGUIMessage;
import game.view.GUIMessage.ModelGUISendsMessage;
import game.view.GUIMessage.SetGUIButtonEnabledMessage;
import game.view.GUIMessage.SetGUICardsLeftMessage;
import game.view.GUIMessage.SetGUIHighlightMessage;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.event.ChangeListener;

import utility.Listener;

public class DummyGUI implements GUI {

	Listener zombieListener, humanListener;
	DummyEventReceiver dummyEventReceiver;
	
	public DummyGUI(Listener zombieListener, Listener humanListener, TriggerEventHandler triggerEventHandler) {
		this.zombieListener = zombieListener;
		this.humanListener = humanListener;
		dummyEventReceiver = new DummyEventReceiver(zombieListener, humanListener, triggerEventHandler);
	}

	@Override
	public void setPlayer(Player player) {
		dummyEventReceiver.setPlayer(player);
	}

	@Override
	public void setButtonEnabled(Button button, boolean aktywny) {
		zombieListener.send(new SetGUIButtonEnabledMessage(button, aktywny));
		humanListener.send(new SetGUIButtonEnabledMessage(button, aktywny));
	}

	@Override
	public Hand getHand(Player player) {
		return new DummyHand(zombieListener, humanListener, player);
	}

	@Override
	public Board getBoard() {
		return new DummyBoard(zombieListener, humanListener);
	}

	@Override
	public EventReceiver getEventReceiver() {
		return dummyEventReceiver;
	}

	@Override
	public void addButtonListener(Button button, ActionListener a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addSliderChangeListener(ChangeListener cl) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCardsLeft(Player player, int left) {
		zombieListener.send(new SetGUICardsLeftMessage(player, left));
		humanListener.send(new SetGUICardsLeftMessage(player, left));
	}

	@Override
	public void modelSendsMessage(String message) {
		zombieListener.send(new ModelGUISendsMessage(message));
		humanListener.send(new ModelGUISendsMessage(message));
	}

	@Override
	public void modelSendsAllMessages(List<String> messages) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void modelSetsAllMessages(List<String> messages) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void drawGlobalModifiers(Iterable<Modifier> modifiers) {
		zombieListener.send(new DrawGUIGlobalModifiersMessage(modifiers));
		humanListener.send(new DrawGUIGlobalModifiersMessage(modifiers));
	}

	@Override
	public void drawHistorySlider(int turn) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHighlight(boolean set) {
		zombieListener.send(new SetGUIHighlightMessage(set));
		humanListener.send(new SetGUIHighlightMessage(set));
	}

	@Override
	public void exit() {
		zombieListener.send(new ExitGUIMessage());
		humanListener.send(new ExitGUIMessage());
	}
}
