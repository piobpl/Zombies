package game.model.traps;

import game.model.Card;
import game.model.GameState;
import game.model.SelectionTester;
import game.model.Trap;
import game.model.Card.CardType;

import java.util.EnumSet;

import utility.Pair;

/**
 * Trap card. Wall absorbs shots, but doesn't explode.
 *
 * @author jerzozwierz
 *
 */
public class WallTrap extends Trap {

	/**
	 *
	 */
	private static final long serialVersionUID = -6959430938058829988L;

	public WallTrap(GameState gameState, int strength,
			Pair<Integer, Integer> coordinates) {
		this.strength = strength;
		this.gameState = gameState;
		this.coX = coordinates.first;
		this.coY = coordinates.second;
		this.coordinates = coordinates;

	}

	public final Integer strength;
	public final Integer coX;
	public final Integer coY;

	private Pair<Integer, Integer> coordinates;
	private GameState gameState;

	@Override
	public String getName() {
		return "Wall (" + strength + ")";
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
		switch (card.getType()) {
		case BARREL:
			return false;
			// jednak, niech beczka ogarnia ze zaraz ma auto
			// i pierdolnie wczesniej - w przeciwnym razie moga
			// wynikac z tego problemy
		case DOGS:
			return false;
		case ZOMBIE:
			if (SelectionTester.areInSameRow(coordinates, from))
				return card.getStrength() >= strength;
			if (coordinates.first + 1 == from.first) {
				return card.getStrength() >= strength;
			}
			// cutest case
			int totalStrength = 0;
			int i = from.first;
			while (i >= 0 && gameState.getBoard().is(i, coY, CardType.ZOMBIE))
				totalStrength += gameState.getBoard().get(i--, coY)
						.getStrength();
			return totalStrength >= strength;
		default:
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void movedOn(Card card) {
		if (card.getType() == CardType.BARREL) {
			gameState.getBoard().set(coX, coY, null);
		}
	}

	@Override
	public EnumSet<Trigger> getTriggers() {
		return EnumSet.of(Trigger.SHOT);
	}

	@Override
	public void trigger() {
	}

	@Override
	public TrapType getType() {
		return TrapType.WALL;
	}

}
