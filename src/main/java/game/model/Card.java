package game.model;

import game.controller.Selection;
import game.controller.Selection.SelectionType;
import game.model.Card.CardType;
import game.model.Modifier.ModifierType;
import game.model.cards.humans.BackOff;
import game.model.cards.humans.Barrel;
import game.model.cards.humans.Barrier;
import game.model.cards.humans.Blood;
import game.model.cards.humans.Burst;
import game.model.cards.humans.Car;
import game.model.cards.humans.Flamethrower;
import game.model.cards.humans.Freeze;
import game.model.cards.humans.Gasoline;
import game.model.cards.humans.GetOut;
import game.model.cards.humans.HandGrenade;
import game.model.cards.humans.HighVoltage;
import game.model.cards.humans.Mine;
import game.model.cards.humans.Napalm;
import game.model.cards.humans.Net;
import game.model.cards.humans.Pit;
import game.model.cards.humans.Searchlight;
import game.model.cards.humans.Shot;
import game.model.cards.humans.Sniper;
import game.model.cards.humans.StreetOnFire;
import game.model.cards.humans.Wall;
import game.model.cards.zombies.Bite;
import game.model.cards.zombies.Change;
import game.model.cards.zombies.Claws;
import game.model.cards.zombies.Dogs;
import game.model.cards.zombies.Human;
import game.model.cards.zombies.Hunger;
import game.model.cards.zombies.Mass;
import game.model.cards.zombies.Meat;
import game.model.cards.zombies.PickAxe;
import game.model.cards.zombies.Terror;
import game.model.cards.zombies.Zombie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utility.Typed;
import utility.TypedSet;

/**
 * An abstract class representing a card.
 */
public abstract class Card implements Typed<CardType>, Serializable {

	private static final long serialVersionUID = -2663209980322004223L;

	public static enum CardType {
		BACKOFF, BARREL, BARRIER, BITE, BLOOD, BURST, CAR, CHANGE, CLAWS, DOGS, FREEZE, FLAMETHROWER, GASOLINE, GETOUT, HANDGRANADE, HIGHVOLTAGE, HUMAN, HUNGER, MASS, MEAT, MINE, NAPALM, NET, PICKAXE, PIT, SEARCHLIGHT, SHOT, SNIPER, STREETONFIRE, TERROR, WALL, ZOMBIE;
	}

	/*
	 * Returns whole player's deck (temporarily not the same set as in rules).
	 */
	public static List<Card> getCompleteDeck(Player player) {
		ArrayList<Card> cards = new ArrayList<Card>();
		if (player == Player.ZOMBIE) {
			cards.add(new Zombie(5));
			cards.add(new Zombie(5));
			cards.add(new Zombie(5));
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
			cards.add(new Dogs());
		} else {
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
			cards.add(new Napalm());
			cards.add(new Net());
			cards.add(new Flamethrower());
			cards.add(new Barrel());
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

	public abstract String getTooltipMessage();

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
