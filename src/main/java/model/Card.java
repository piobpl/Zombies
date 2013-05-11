package model;

import java.util.ArrayList;
import java.util.List;

import controller.Selection;
import controller.Selection.SelectionType;

public abstract class Card {
	
	public static List<Card> getCompleteDeck(Player player){
		return new ArrayList<Card>();
	}

	public abstract boolean isSelectionCorrect(GameState gameState, Selection selection);
	public abstract void makeEffect(Selection selection, GameState gameState);
	
	public abstract String getName();
	public abstract SelectionType getSelectionType();
	public abstract Integer getStrength();
	public abstract void setStrength(Integer strength);
	
}
