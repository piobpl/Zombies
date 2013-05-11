package controller;

import java.util.HashMap;

import model.Card;
import view.EventReceiver;
import view.EventReceiver.BoardClickedEvent;
import view.EventReceiver.Event;
import view.EventReceiver.EventType;
import controller.Selection.CellSelection;
import controller.Selection.ColumnSelection;
import controller.Selection.GroupSelection;
import controller.Selection.HandSelection;
import controller.Selection.SelectionType;

public class Selector {
	public final EventReceiver eventReceiver;
	private HashMap<SelectionType, Selection> selectionMap;
	public Selector(EventReceiver eventReceiver) {
		this.eventReceiver = eventReceiver;
		selectionMap=new HashMap<SelectionType, Selection>();
		selectionMap.put(SelectionType.CELL, new CellSelection());
		selectionMap.put(SelectionType.COLUMN, new ColumnSelection());
		selectionMap.put(SelectionType.GROUP, new GroupSelection());
		selectionMap.put(SelectionType.HAND, new HandSelection());
	}

	public Selection getSelection(Card card) {
		Selection s=selectionMap.get(card.getSelectionType());
		while(true){
			Event e=eventReceiver.getNextEvent();
			if(e.type==EventType.ApplyButtonClicked)
				return s;
			if(e.type==EventType.CancelButtonClicked)
				return null;
			if(e.type==EventType.BoardClicked){
				BoardClickedEvent f=(BoardClickedEvent) e;
				Selection tmp=s.add(f.cardClicked);
				/*if(card.isSelectionCorrect(gameState, tmp)){
					s=tmp;
				}*/
			}
		}
		//return null;
	}
}
