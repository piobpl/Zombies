package game.model.traps;

import game.model.Card;
import game.model.DamageDealer;
import game.model.GameState;
import game.model.SelectionTester;
import game.model.Trap;

import java.util.EnumSet;

import utility.Pair;

/**
 * Trap card. Does't absorb shots, explodes when someone step onto, or when is
 * getting damaged by fire or other explosion
 *
 * @author jerzozwierz
 *
 */
public class MineTrap extends Trap {

	/**
	 * 
	 */
	private static final long serialVersionUID = -482795533765600308L;

	public MineTrap(GameState gameState, Pair<Integer, Integer> coordinates) {
		this.gameState = gameState;
		this.coX = coordinates.first;
		this.coY = coordinates.second;
		this.coordinates = coordinates;
	}

	public final Integer coX;
	public final Integer coY;

	private GameState gameState;
	private Pair<Integer, Integer> coordinates;

	@Override
	public String getName() {
		return "Mine";
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
		return true;
	}

	@Override
	public void movedOn(Card card) {
		trigger();
	}

	@Override
	public EnumSet<Trigger> getTriggers() {
		return EnumSet.of(Trigger.FIRE, Trigger.EXPLOSION);
	}

	@Override
	public void trigger() {
		gameState.getBoard().getTraps(coX, coY).remove(this);
		DamageDealer.dealDamage(gameState, coX, coY, 2, Trigger.EXPLOSION);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				Pair<Integer, Integer> temp = new Pair<Integer, Integer>(i, j);
				if (SelectionTester.areEdgeAdjacent(temp, coordinates))
					DamageDealer.dealDamage(gameState, i, j, 1,
							Trigger.EXPLOSION);
			}
		}
	}

	@Override
	public TrapType getType() {
		return TrapType.MINE;
	}

}
