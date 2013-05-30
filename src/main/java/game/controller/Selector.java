package game.controller;

import game.controller.Selection.CellSelection;
import game.controller.Selection.ColumnSelection;
import game.controller.Selection.EmptySelection;
import game.controller.Selection.GroupSelection;
import game.controller.Selection.HandSelection;
import game.controller.Selection.MultiGroupSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.Player;
import game.view.EventReceiver;
import game.view.EventReceiver.BoardClickedEvent;
import game.view.EventReceiver.ButtonClickedEvent;
import game.view.EventReceiver.ClickEvent;
import game.view.EventReceiver.EventType;
import game.view.EventReceiver.HandClickedEvent;
import game.view.GUI;
import game.view.GUI.Board;
import game.view.GUI.Button;

import java.awt.event.MouseEvent;
import java.util.EnumMap;

import utility.Pair;

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
		this.eventReceiver = gui.getEventReceiver();
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
			ClickEvent e = null;
			BoardClickedEvent f = null;
			HandClickedEvent g = null;
			ButtonClickedEvent h = null;
			if (card.getSelectionType() == SelectionType.EMPTY) {
				current = candidate = new EmptySelection();
				currentRate = 2;
			}
			while (true) {
				gui.setButtonEnabled(Button.ApplySelection, currentRate == 2);
				e = eventReceiver.getNextClickEvent();
				if (e.type == EventType.ButtonClicked) {
					if (e.info.getButton() != MouseEvent.BUTTON1)
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
					if (e.info.getButton() != MouseEvent.BUTTON1)
						continue;
					if (gameState.getHand(Player.ZOMBIE).get(
							((HandClickedEvent) e).cardClicked) == card
							|| gameState.getHand(Player.HUMAN).get(
									((HandClickedEvent) e).cardClicked) == card) {
						return null;
					}
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
						if (e.info.getButton() == MouseEvent.BUTTON1) {
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
						if (e.info.getButton() != MouseEvent.BUTTON1)
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
						// board.setHighlight(false);
						board.clearGlassText();
						int k = 0;
						if (current != null)
							for (Pair<Integer, Integer> p : ((GroupSelection) current).cells) {
								board.getCell(p.first, p.second).setHighlight(
										false);
							}
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
						board.setHighlight(false);
						board.clearGlassText();
						int[][] cnt = new int[5][3];
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
