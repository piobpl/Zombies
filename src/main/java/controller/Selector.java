package controller;

import java.awt.event.MouseEvent;
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
import view.GUI;
import view.GUI.Button;
import controller.Selection.CellSelection;
import controller.Selection.ColumnSelection;
import controller.Selection.EmptySelection;
import controller.Selection.GroupSelection;
import controller.Selection.HandSelection;
import controller.Selection.MultiGroupSelection;
import controller.Selection.SelectionType;

/**
 *
 * @author Edoipi, piob
 *
 */
public class Selector {
	public final EventReceiver eventReceiver;
	private GameState gameState;
	private GUI gui;
	private EnumMap<SelectionType, Selection> selectionMap;

	public Selector(GameState gameState, GUI gui) {
		this.gameState = gameState;
		this.gui = gui;
		this.eventReceiver = gui.eventReceiver;
		selectionMap = new EnumMap<SelectionType, Selection>(
				SelectionType.class);
		selectionMap.put(SelectionType.CELL, new CellSelection());
		selectionMap.put(SelectionType.COLUMN, new ColumnSelection());
		selectionMap.put(SelectionType.GROUP, new GroupSelection());
		selectionMap.put(SelectionType.MULTIGROUP, new MultiGroupSelection());
		selectionMap.put(SelectionType.HAND, new HandSelection());
		selectionMap.put(SelectionType.EMPTY, new EmptySelection());
	}

	public Selection getSelection(Card card) {
		try {
			gui.setButtonEnabled(Button.EndTurn, false);
			gui.setButtonEnabled(Button.ApplySelection, false);
			gui.setButtonEnabled(Button.CancelSelection, true);
			Selection current = null, candidate = null;
			int currentRate = 0, candidateRate;
			Board board = gui.getBoard();
			Event e = null;
			BoardClickedEvent f = null;
			HandClickedEvent g = null;
			ButtonClickedEvent h = null;
			if (card.getSelectionType() == SelectionType.EMPTY) {
				current = candidate = new EmptySelection();
				currentRate = 2;
			}
			while (true) {
				gui.setButtonEnabled(Button.ApplySelection, currentRate == 2);
				e = eventReceiver.getNextEvent();
				if (e.type == EventType.ButtonClicked) {
					if (e.mouseButtonId != MouseEvent.BUTTON1)
						continue;
					h = (ButtonClickedEvent) e;
					if (h.button == Button.ApplySelection) {
						if (currentRate == 2)
							return current;
						else
							continue;
					} else if (h.button == Button.CancelSelection)
						return null;
					else
						continue;
				} else if (e.type == EventType.HandClicked) {
					if (e.mouseButtonId != MouseEvent.BUTTON1)
						continue;
					if (card.getSelectionType() != SelectionType.HAND)
						continue;
					g = (HandClickedEvent) e;
					candidate = new HandSelection(g.player, g.cardClicked);
				} else {
					if (card.getSelectionType() == SelectionType.HAND
							|| card.getSelectionType() == SelectionType.EMPTY)
						continue;
					f = (BoardClickedEvent) e;
					if (card.getSelectionType() == SelectionType.MULTIGROUP) {
						if (e.mouseButtonId == MouseEvent.BUTTON1) {
							if (current == null)
								candidate = selectionMap.get(
										card.getSelectionType()).add(
										f.cardClicked);
							else
								candidate = current.add(f.cardClicked);
						} else {
							if (current == null)
								candidate = ((MultiGroupSelection) selectionMap
										.get(card.getSelectionType()))
										.remove(f.cardClicked);
							else
								candidate = ((MultiGroupSelection) current)
										.remove(f.cardClicked);
						}
					} else {
						if (e.mouseButtonId != MouseEvent.BUTTON1)
							continue;
						if (current == null)
							candidate = selectionMap.get(
									card.getSelectionType()).add(f.cardClicked);
						else
							candidate = current.add(f.cardClicked);
					}
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
						gui.setHighlight(false);
						int k = 0;
						board.clearGlassText();
						for (Pair<Integer, Integer> p : ((GroupSelection) candidate).cells) {
							board.getCell(p.first, p.second).setGlassText(
									"" + (++k));
							board.getCell(p.first, p.second).setHighlight(true);
						}
						break;
					case HAND:
						if (current != null) {
							HandSelection pos = ((HandSelection) current);
							gui.getHand(pos.player).getCell(pos.card)
									.setHighlight(false);
						}
						HandSelection pos = ((HandSelection) candidate);
						gui.getHand(pos.player).getCell(pos.card)
								.setHighlight(true);
						break;
					case EMPTY:
						break;
					case MULTIGROUP:
						int[][] cnt = new int[5][3];
						board.clearGlassText();
						for (Pair<Integer, Integer> p : ((MultiGroupSelection) candidate).cells)
							++cnt[p.first][p.second];
						for (int i = 0; i < 5; ++i)
							for (int j = 0; j < 3; ++j) {
								if (cnt[i][j] > 0) {
									board.getCell(i, j).setGlassText(
											"" + cnt[i][j]);
									board.getCell(i, j).setHighlight(true);
								}
							}
						break;
					default:
						break;
					}
					current = candidate;
					currentRate = candidateRate;
				}
			}
		} finally {
			gui.setButtonEnabled(Button.EndTurn, true);
			gui.setButtonEnabled(Button.ApplySelection, false);
			gui.setButtonEnabled(Button.CancelSelection, false);
			gui.getBoard().clearGlassText();
		}
	}
}
