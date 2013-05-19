package model;

import java.util.ArrayList;
import java.util.List;

import model.Card.CardType;
import model.Modifier.ModifierType;
import model.cards.humans.BackOff;
import model.cards.humans.Barrier;
import model.cards.humans.Blood;
import model.cards.humans.Burst;
import model.cards.humans.Car;
import model.cards.humans.Freeze;
import model.cards.humans.Gasoline;
import model.cards.humans.GetOut;
import model.cards.humans.HandGrenade;
import model.cards.humans.HighVoltage;
import model.cards.humans.Mine;
import model.cards.humans.Pit;
import model.cards.humans.Searchlight;
import model.cards.humans.Shot;
import model.cards.humans.Sniper;
import model.cards.humans.StreetOnFire;
import model.cards.humans.Wall;
import model.cards.zombies.Bite;
import model.cards.zombies.Change;
import model.cards.zombies.Claws;
import model.cards.zombies.Human;
import model.cards.zombies.Hunger;
import model.cards.zombies.Mass;
import model.cards.zombies.Meat;
import model.cards.zombies.PickAxe;
import model.cards.zombies.Terror;
import model.cards.zombies.Zombie;
import utility.Typed;
import utility.TypedSet;
import controller.Selection;
import controller.Selection.SelectionType;

/**
 * An abstract class representing a card.
 */
public abstract class Card implements Typed<CardType> {

	public static enum CardType {
		BACKOFF, BARREL, BARRIER, BITE, BLOOD, BURST, CAR, CHANGE, CLAWS, DOGS, FREEZE, GASOLINE, GETOUT, HANDGRANADE, HIGHVOLTAGE, HUMAN, HUNGER, MASS, MEAT, MINE, PICKAXE, PIT, SEARCHLIGHT, SHOT, SNIPER, STREETONFIRE, TERROR, WALL, ZOMBIE;
	}

	/*
	 * Returns whole player's deck (temporarily not the same set as in rules).
	 */
	public static List<Card> getCompleteDeck(Player player) {
		ArrayList<Card> cards = new ArrayList<Card>();
		if (player == Player.ZOMBIE) {
			cards.add(new Zombie(5));
			cards.add(new Zombie(4));
			cards.add(new Zombie(3));
			cards.add(new Zombie(2));
			cards.add(new Bite());
			cards.add(new Change());
			cards.add(new Claws());
			cards.add(new Human());
			cards.add(new Hunger());
			cards.add(new Mass());
			cards.add(new Meat());
			cards.add(new PickAxe());
			cards.add(new Terror());
		} else {
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new Blood());
			cards.add(new BackOff());
			cards.add(new Barrier());
			cards.add(new Burst(2));
			cards.add(new Car());
			cards.add(new Freeze());
			cards.add(new Gasoline());
			cards.add(new GetOut());
			cards.add(new HandGrenade());
			cards.add(new HighVoltage());
			cards.add(new Mine());
			cards.add(new Pit(3));
			cards.add(new Searchlight());
			cards.add(new Shot(3));
			cards.add(new Sniper());
			cards.add(new StreetOnFire());
			cards.add(new Wall(6));
		}
		return cards;
	}

	private TypedSet<Modifier, ModifierType> modifiers = new TypedSet<>();

	public TypedSet<Modifier, ModifierType> getModifiers() {
		return modifiers;
	}

	public abstract String getName();

	public abstract SelectionType getSelectionType();

	public abstract Integer getStrength();

	public abstract void makeEffect(Selection selection, GameState gameState);

	public void nextStage() {
		TypedSet<Modifier, ModifierType> futureModifiers = new TypedSet<>();
		for (Modifier m : futureModifiers) {
			m.decreaseTime();
			if (m.getTime() > 0)
				futureModifiers.add(m);
		}
		modifiers = futureModifiers;
	}

	public abstract int rateSelection(GameState gameState, Selection selection);

	public abstract void setStrength(Integer strength);

}
