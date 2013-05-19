package model.traps;

import java.util.EnumSet;

import model.Card;
import model.Card.CardType;
import model.GameState;
import model.SelectionTester;
import model.Trap;
import utility.Pair;

/**
 * Trap card. Wall absorbs shots, but doesn't explode.
 *
 * @author jerzozwierz
 *
 */
public class WallTrap extends Trap {

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
		return "Wall";
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
			// Nie bedzie teleportacji:
			/*
			 * if (!SolidityTester.areEdgeAdjacent(coordinates, from)) { throw
			 * new RuntimeException() { private static final long
			 * serialVersionUID = 1L;
			 *
			 * @Override public String toString() { return
			 * "Spierdalaj mi z tym"; } }; }
			 */
			if (SelectionTester.areInSameRow(coordinates, from))
				return card.getStrength() >= strength;
			// cofanie na mur jest jak ruch z boku, sprawdzamy tylko sile, jest
			// dokladnie w regulach
			if (coordinates.first + 1 == from.first) {
				// jesli przy cofaniu (np przez reflektor) tez trzeba
				// uwzgledniac szeregi zombich,
				// to ja pierdole te robote
				return card.getStrength() >= strength;
			}
			// cutest case
			int totalStrength = 0;
			int i = from.first;
			while (!gameState.getBoard().is(i, coY, CardType.ZOMBIE))
				totalStrength += gameState.getBoard().get(i--, coY)
						.getStrength();
			return totalStrength >= strength;
			// nie jestem pewny, czy przy poruszeniu zombiaka
			// glodem, tez nalezy uwzgledniac szeregi - jesli nie,
			// w tym wypadku lepiej zeby zajela sie
			// tym karta glod
			// nie ma dodatkowych zalozen, zrobmy jak nam wygodniej - jak idze w
			// przod to kolezki zawsze go pchna
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
		// kolejna poprawka - mur ma byc czuly na strzal, ale
		// nic nie robic
	}

	@Override
	public void trigger() {
	}

	@Override
	public TrapType getType() {
		return TrapType.WALL;
	}

}
