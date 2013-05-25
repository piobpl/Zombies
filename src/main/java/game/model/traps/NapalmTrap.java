package game.model.traps;

import game.model.Card;
import game.model.DamageDealer;
import game.model.GameState;
import game.model.Trap;

import java.util.EnumSet;

import utility.Pair;

/**
 * @author piob
 */
public class NapalmTrap extends Trap {

	/**
	 * 
	 */
	private static final long serialVersionUID = 232363502609840692L;

	public NapalmTrap(GameState gameState, Pair<Integer, Integer> coordinates) {
		this.gameState = gameState;
		this.coX = coordinates.first;
		this.coY = coordinates.second;
		time = 8;
	}

	public final Integer coX;
	public final Integer coY;

	private GameState gameState;
	private int time;

	@Override
	public String getName() {
		return "Napalm";
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public void decreaseTime() {
		--time;
	}

	@Override
	public boolean isMovePossible(Card card, Pair<Integer, Integer> from) {
		return true;
	}

	@Override
	public void movedOn(Card card) {
		DamageDealer.dealDamage(gameState, coX, coY, 1, Trigger.HOLLOW);
	}

	@Override
	public EnumSet<Trigger> getTriggers() {
		return EnumSet.noneOf(Trigger.class);
	}

	@Override
	public void trigger() {

	}

	@Override
	public TrapType getType() {
		return TrapType.NAPALM;
	}

}
