package model;

import controller.Selection;
import controller.Selection.CellSelection;
import controller.Selection.SelectionType;

public enum Card {
	// TODO
	A() {
		
		@SuppressWarnings("unused")
		@Override
		public boolean isSelectionCorrect(Selection unknownTypeSelection) {
			CellSelection selection = (CellSelection) unknownTypeSelection;
			return false;
		}

		@Override
		public void makeEffect(Selection selection, GameState gameState) {
		}
	};
	public final SelectionType selectionType = SelectionType.CELL;

	public abstract boolean isSelectionCorrect(Selection selection);

	public abstract void makeEffect(Selection selection, GameState gameState);
}
