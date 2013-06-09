package game.view;

import game.model.Modifier;
import game.model.Player;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.event.ChangeListener;

public class DummyGUI implements GUI {

	@Override
	public void setPlayer(Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setButtonEnabled(Button button, boolean aktywny) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Hand getHand(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Board getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventReceiver getEventReceiver() {
		throw new UnsupportedOperationException();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modelSendsMessage(String message) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawHistorySlider(int turn) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHighlight(boolean set) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

}
