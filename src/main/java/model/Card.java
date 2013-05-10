package model;

import java.util.ArrayList;
import java.util.List;

import controller.Selection;
import controller.Selection.SelectionType;

public abstract class Card {
	public static List<Card> getCompleteDeck(Player player){
		return new ArrayList<Card>();
	}

	// TODO
	public final String name = "Abstract";
	public final int strength = -1;
	public final SelectionType selectionType = SelectionType.CELL;
	public abstract boolean isSelectionCorrect(GameState gameState, Selection selection);
	public abstract void makeEffect(Selection selection, GameState gameState);
}
