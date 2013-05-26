package game.controller;

import game.model.Deck;
import game.model.GameState;
import game.model.Hand;
import game.model.Player;
import game.view.GUI;

public class DrawingStage implements Stage {
	public final GameState gameState;
	public final GUI gui;

	public DrawingStage(GameState gameState, GUI gui) {
		this.gameState = gameState;
		this.gui = gui;
	}

	public void perform(Player player) {
		Hand hand = gameState.getHand(player);
		Deck deck = gameState.getDeck(player);
		for (int i = 0; i < 4; ++i)
			if (hand.get(i) == null) {
				if (deck.isEmpty()) {
					if (player == Player.ZOMBIE)
						throw new GameOver(Player.HUMAN,gameState);
					else
						return;
				} else {
					hand.set(i, deck.getTopCard());
					System.err.println("Card drawn: "
							+ hand.get(i).getName());
				}
			}
		gameState.update();
	}

	@Override
	public StageType getStageType() {
		return StageType.DRAWING;
	}
}
