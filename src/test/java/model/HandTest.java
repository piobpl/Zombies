package model;

import static org.junit.Assert.assertEquals;
import game.model.Card;
import game.model.GameState;
import game.model.Hand;
import game.model.Player;
import game.model.cards.zombies.Zombie;
import game.view.SimpleGUI;

import org.junit.Test;


public class HandTest {

	@Test
	public void test() {
		Hand hand = new Hand(new GameState(new SimpleGUI(null, null)), Player.ZOMBIE);
		for (int i = 0; i < 4; ++i)
			assertEquals(null, hand.get(i));
		Card[] cards = new Card[4];
		for (int i = 0; i < 4; ++i) {
			cards[i] = new Zombie(i);
			hand.set(i, cards[i]);
		}
		for (int i = 0; i < 4; ++i)
			assertEquals(cards[i], hand.get(i));
	}

}
