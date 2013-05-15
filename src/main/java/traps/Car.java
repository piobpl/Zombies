package traps;

import cards.helpers.DamageDealer;
import cards.helpers.SolidityTester;
import model.Card;
import model.GameState;
import model.Trap;
import utility.Pair;
/**
 * Trap card.
 * @author jerzozwierz
 *
 */
public class Car extends Trap {

	public Car(GameState gameState, Pair<Integer, Integer> coordinates) {
		coX = coordinates.first;
		coY = coordinates.second;
		this.gameState = gameState;
		this.coordinates = coordinates;
	}

	public final Integer coX;
	public final Integer coY;
	private final Pair<Integer, Integer> coordinates;
	private GameState gameState;

	@Override
	public String getName() {
		return "Car";
	}

	@Override
	public int getTime() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void decreaseTime() {}
	//best solution, I guess
	// tak :P ~piob

	@Override
	public boolean isMovePossible(Card card, Pair<Integer, Integer> from) {
		return !card.getName().equals("Barrel");
	}

	@Override
	public void movedOn(Card card) {}

	@Override
	public void shot() {
		explode();
		//TODO delete this shit
	}

	@Override
	public void fire() {
		explode();
		//TODO delete this shit
	}

	@Override
	public void blowUp() {
		explode();
		//TODO delete this shit
	}

	private void explode() {
		for (int i=0; i<5; i++) {
			for (int j=0; j<3; j++) {
				Pair<Integer, Integer> temp = new Pair<Integer, Integer>(i,j);
				if (SolidityTester.areVertexAdjacent(temp, coordinates) ||
						temp.equals(coordinates)) {
					DamageDealer.dealExplosionDamage(gameState, i, j, 1);
				}
			}
		}
	}

}
