package cards.zombies;

import controller.Selection;
import controller.Selection.HandSelection;
import controller.Selection.SelectionType;
import model.Card;
import model.GameState;
import model.Player;

public class Meat extends Card {

	@Override
	public boolean isSelectionCorrect(GameState gameState, Selection selection) {
		Player player = ((HandSelection)selection).player;
		Integer card = ((HandSelection)selection).card;
		return (player == Player.HUMAN && gameState.getHand(player).get(card) != null);
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer card = ((HandSelection)selection).card;
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

}
