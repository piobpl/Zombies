package traps;

import java.util.EnumSet;

import model.Card;
import model.GameState;
import model.Trap;
import utility.Pair;
import cards.helpers.DamageDealer;
import cards.helpers.SolidityTester;
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

	@Override
	public boolean isMovePossible(Card card, Pair<Integer, Integer> from) {
		return !card.getName().equals("Barrel");
	}

	@Override
	public void movedOn(Card card) {}

	public EnumSet<Trigger> getTriggers(){
		return EnumSet.allOf(Trigger.class);
	}

	public void trigger() {
		for (int i=0; i<5; i++) {
			for (int j=0; j<3; j++) {
				Pair<Integer, Integer> temp = new Pair<Integer, Integer>(i,j);
				if (SolidityTester.areVertexAdjacent(temp, coordinates) ||
						temp.equals(coordinates)) {
					DamageDealer.dealDamage(gameState, i, j, 1, Trigger.EXPLOSION);
				}
			}
		}
	}

}
