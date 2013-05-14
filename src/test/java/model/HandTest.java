package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import view.GUI;
import cards.zombies.Zombie;

public class HandTest {

	@Test
	public void test() {
		Hand hand = new Hand(new GameState(new GUI()), Player.ZOMBIE);
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
