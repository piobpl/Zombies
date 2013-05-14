package controller;

import java.util.EnumMap;

import model.Card;
import model.GameState;
import utility.Pair;
import view.Board;
import view.EventReceiver;
import view.EventReceiver.BoardClickedEvent;
import view.EventReceiver.ButtonClickedEvent;
import view.EventReceiver.Event;
import view.EventReceiver.EventType;
import view.EventReceiver.HandClickedEvent;
import view.GUI.Button;
import controller.Selection.CellSelection;
import controller.Selection.ColumnSelection;
import controller.Selection.GroupSelection;
import controller.Selection.HandSelection;
import controller.Selection.SelectionType;

public class Selector {
	public final EventReceiver eventReceiver;
	private GameState gameState;
	private EnumMap<SelectionType, Selection> selectionMap;

	public Selector(EventReceiver eventReceiver, GameState gameState) {
		this.eventReceiver = eventReceiver;
		this.gameState = gameState;
		selectionMap = new EnumMap<SelectionType, Selection>(
				SelectionType.class);
		selectionMap.put(SelectionType.CELL, new CellSelection());
		selectionMap.put(SelectionType.COLUMN, new ColumnSelection());
		selectionMap.put(SelectionType.GROUP, new GroupSelection());
		selectionMap.put(SelectionType.HAND, new HandSelection());
	}

	public Selection getSelection(Card card) {
		Selection current = null, candidate = null;
		int currentRate = 0, candidateRate;
		Board board = gameState.gui.getBoard();
		while (true) {
			Event e = eventReceiver.getNextEvent();
			if (e.type == EventType.ButtonClicked) {
				ButtonClickedEvent f = (ButtonClickedEvent) e;
				if (f.button == Button.ApplySelection)
					return currentRate == 2 ? current : null;
				else if (f.button == Button.CancelSelection)
					return null;
				else
					continue;
			}
			if (e.type == EventType.BoardClicked) {
				BoardClickedEvent f = (BoardClickedEvent) e;
				if (card.getSelectionType() == SelectionType.HAND) {
					HandClickedEvent g = (HandClickedEvent) e;
					candidate = new HandSelection(g.player, g.cardClicked);
				} else {
					if (current == null)
						candidate = selectionMap.get(card.getSelectionType())
								.add(f.cardClicked);
					else
						candidate = current.add(f.cardClicked);
				}
				candidateRate = card.rateSelection(gameState, candidate);
				if (candidateRate > 0) {

					switch (card.getSelectionType()) {
					case CELL:
						if (current != null) {
							Pair<Integer, Integer> pos = ((CellSelection) current).cell;
							board.getCell(pos.first, pos.second).setHighlight(
									false);
						}
						{
							Pair<Integer, Integer> pos = ((CellSelection) candidate).cell;
							board.getCell(pos.first, pos.second).setHighlight(
									true);
						}
						break;
					case COLUMN:
						if (current != null) {
							board.setColumnHighlight(
									((ColumnSelection) current).column, false);
						}
						board.setColumnHighlight(
								((ColumnSelection) candidate).column, true);
						break;
					case GROUP:
						board.getCell(f.cardClicked.first, f.cardClicked.second)
								.toggleHighlight();
						break;
					case HAND:
						if (current != null) {
							HandSelection pos = ((HandSelection) current);
							gameState.gui.getHand(pos.player).getCell(pos.card)
									.setHighlight(false);
						}
						HandSelection pos = ((HandSelection) candidate);
						gameState.gui.getHand(pos.player).getCell(pos.card)
								.setHighlight(true);
						break;
					}
					current = candidate;
					currentRate = candidateRate;
				}
			}
		}
	}
}
