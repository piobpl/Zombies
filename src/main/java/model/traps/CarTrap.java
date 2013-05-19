package model.traps;

import java.util.EnumSet;

import model.Card;
import model.Card.CardType;
import model.DamageDealer;
import model.GameState;
import model.SelectionTester;
import model.Trap;
import utility.Pair;

/**
 * Trap card. Car absorbs shots, explodes when shot.
 *
 * @author jerzozwierz
 *
 */
public class CarTrap extends Trap {

	public CarTrap(GameState gameState, Pair<Integer, Integer> coordinates) {
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
	public void decreaseTime() {
	}

	@Override
	public boolean isMovePossible(Card card, Pair<Integer, Integer> from) {
		return card.getType() != CardType.BARREL;
	}

	@Override
	public void movedOn(Card card) {
	}

	public EnumSet<Trigger> getTriggers() {
		return EnumSet.complementOf(EnumSet.of(Trigger.VOLTAGE));
	}

	public void trigger() {
		gameState.getBoard().getTraps(coX, coY).remove(this);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				Pair<Integer, Integer> temp = new Pair<Integer, Integer>(i, j);
				if (SelectionTester.areVertexAdjacent(temp, coordinates)
						|| temp.equals(coordinates)) {
					DamageDealer.dealDamage(gameState, i, j, 1,
							Trigger.EXPLOSION);
				}
			}
		}
	}

	@Override
	public TrapType getType() {
		return TrapType.CAR;
	}

}
