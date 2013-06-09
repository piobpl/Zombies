package game.model.cards.zombies;

import game.controller.LocalController;
import game.model.Player;
import game.model.traps.WallTrap;

import org.junit.Test;

import utility.Pair;

public class MassTest {

	@Test
	public void test() {
		LocalController loc = new LocalController();
		loc.gameState.getBoard().set(2, 1, new Zombie(5));
		loc.gameState.getBoard().getTraps(3, 1).add(
				new WallTrap(loc.gameState, 5, new Pair<Integer, Integer>(3,1)));
		loc.gameState.getBoard().set(2, 0, new Zombie(3));
		loc.gameState.getHand(Player.ZOMBIE).set(0, new Mass());
		loc.game(Player.ZOMBIE);
	}

}
