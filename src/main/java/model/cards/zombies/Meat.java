package model.cards.zombies;

import model.Card;
import model.GameState;
import model.Player;
import controller.Selection;
import controller.Selection.HandSelection;
import controller.Selection.SelectionType;

public class Meat extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Player player = ((HandSelection) selection).player;
		Integer card = ((HandSelection) selection).card;
		if (player == Player.HUMAN
				&& gameState.getHand(player).get(card) != null)
			return 2;
		return 0;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer card = ((HandSelection) selection).card;
		gameState.getHand(Player.HUMAN).remove(card);
	}

	@Override
	public String getName() {
		return "Meat";
	}

	@Override
	public SelectionType getSelectionType() {
		return SelectionType.HAND;
	}

	@Override
	public Integer getStrength() {
		return null;
	}

	@Override
	public void setStrength(Integer strength) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public CardType getType() {
		return CardType.MASS;
	}

	@Override
	public String getTooltipMessage() {
		return "It forces human player to remove one card (chosen by a zombie player) from his hand.";
	}

}
