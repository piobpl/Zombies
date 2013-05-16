package traps;

import java.util.EnumSet;

import model.Card;
import model.GameState;
import model.Trap;
import modifiers.ModifierType;
import utility.Pair;
/**
 * Trap card.
 * Doesn't absorb shots.
 * @author jerzozwierz
 *
 */
public class PitTrap extends Trap {

	public PitTrap(GameState gameState, int strength, Pair<Integer, Integer> coordinates) {
		coX = coordinates.first;
		coY = coordinates.second;
		this.gameState = gameState;
		this.strength = strength;
	}

	public final Integer coX;
	public final Integer coY;
	public final Integer strength;
	private GameState gameState;
	
	
	@Override
	public String getName() {
		return "Pit";
	}

	@Override
	public int getTime() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void decreaseTime() {}

	@Override
	public boolean isMovePossible(Card card, Pair<Integer, Integer> from) {
		return true;
	}

	@Override
	public void movedOn(Card card) {
		switch (card.getName()) {
		case "Barrel": {
			gameState.getBoard().set(coX, coY, null);
			//important: Barrel doesn't explode in this case
			gameState.getBoard().getTraps(coX, coY).remove(this);
			//not sure if it works
			break;
		}
		case "Zombie": {
			if (card.modifiers.contains(ModifierType.HUMAN)) {
				card.modifiers.remove(ModifierType.HUMAN);
				gameState.getBoard().getTraps(coX, coY).remove(this);
				break;
			}
			if (card.getStrength() <= strength) {
				gameState.getBoard().set(coX, coY, null);
				gameState.getBoard().getTraps(coX, coY).remove(this);
			}
			break;
		}
		case "Dogs": {
			if (card.getStrength() <= strength) {
				gameState.getBoard().set(coX, coY, null);
				gameState.getBoard().getTraps(coX, coY).remove(this);
			}
			break;
		}
		}
	}


	@Override
	public EnumSet<Trigger> getTriggers() {
		return EnumSet.noneOf(Trigger.class);
		//empty set (probably :P)
	}

	@Override
	public void trigger() {}

}
