package game.model.cards.humans;

import game.controller.Selection;
import game.controller.Selection.HandSelection;
import game.controller.Selection.SelectionType;
import game.model.Card;
import game.model.GameState;
import game.model.Player;

public class GetOut extends Card {

	@Override
	public int rateSelection(GameState gameState, Selection selection) {
		Player player = ((HandSelection) selection).player;
		Integer card = ((HandSelection) selection).card;
		if (player == Player.ZOMBIE
				&& gameState.getHand(player).get(card) != null)
			return 2;
		return 0;
	}

	@Override
	public void makeEffect(Selection selection, GameState gameState) {
		Integer card = ((HandSelection) selection).card;
		gameState.getHand(Player.ZOMBIE).remove(card);
	}

	@Override
	public String getName() {
		return "Get out!";
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
		return CardType.GETOUT;
	}

	@Override
	public String getTooltipMessage() {
		return "<html>It forces the zombie player to remove one card (chosen by the human player) from his hand.</html>";
	}

}