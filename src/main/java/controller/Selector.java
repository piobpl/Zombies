package controller;

import controller.Selection.SelectionType;
import view.EventReceiver;
import model.Card;

public class Selector {
	public final EventReceiver eventReceiver;

	public Selector(EventReceiver eventReceiver) {
		this.eventReceiver = eventReceiver;
	}

	public static Selection getSelection(Card card) {
		return null;
	}
}
