package controller;

import java.util.HashMap;

import model.Card;
import model.GameState;
import view.EventReceiver;
import view.EventReceiver.BoardClickedEvent;
import view.EventReceiver.Event;
import view.EventReceiver.EventType;
import view.EventReceiver.HandClickedEvent;
import controller.Selection.CellSelection;
import controller.Selection.ColumnSelection;
import controller.Selection.GroupSelection;
import controller.Selection.HandSelection;
import controller.Selection.SelectionType;

public class Selector {
	public final EventReceiver eventReceiver;
	private GameState gameState;
	private HashMap<SelectionType, Selection> selectionMap;

	public Selector(EventReceiver eventReceiver, GameState gameState) {
		this.eventReceiver = eventReceiver;
		this.gameState = gameState;
		selectionMap = new HashMap<SelectionType, Selection>();
		selectionMap.put(SelectionType.CELL, new CellSelection());
		selectionMap.put(SelectionType.COLUMN, new ColumnSelection());
		selectionMap.put(SelectionType.GROUP, new GroupSelection());
		selectionMap.put(SelectionType.HAND, new HandSelection());
	}

	private Selection handClick(Card card) {
		HandSelection s = null;
		while (true) {
			Event e = eventReceiver.getNextEvent();
			if (e.type == EventType.HandClicked) {
				HandClickedEvent f = (HandClickedEvent) e;
				s = new HandSelection(f.player, f.cardClicked);
				if (card.isSelectionCorrect(gameState, s)) {
					return s;
				}
			}
		}
	}

	public Selection getSelection(Card card) {
		if (card.getSelectionType() == SelectionType.HAND)
			return handClick(card);
		Selection s = selectionMap.get(card.getSelectionType());
		while (true) {
			Event e = eventReceiver.getNextEvent();
			if (e.type == EventType.ApplyButtonClicked)
				return s;
			if (e.type == EventType.CancelButtonClicked)
				return null;
			if (e.type == EventType.BoardClicked) {
				BoardClickedEvent f = (BoardClickedEvent) e;
				Selection tmp = s.add(f.cardClicked);
				if (card.isSelectionCorrect(gameState, tmp)) {
					s = tmp;
				}
			}
		}
		// return null;
	}
}
