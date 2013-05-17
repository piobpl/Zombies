package model;

import java.util.ArrayList;
import java.util.List;

import model.cards.humans.Burst;
import model.cards.humans.Gasoline;
import model.cards.humans.GetOut;
import model.cards.humans.HandGrenade;
import model.cards.humans.HighVoltage;
import model.cards.humans.Searchlight;
import model.cards.humans.Shot;
import model.cards.humans.Sniper;
import model.cards.humans.StreetOnFire;
import model.cards.zombies.Claws;
import model.cards.zombies.Hunger;
import model.cards.zombies.Mass;
import model.cards.zombies.Meat;
import model.cards.zombies.Terror;
import model.cards.zombies.Zombie;
import model.modifiers.ModifierSet;
import controller.Selection;
import controller.Selection.SelectionType;

/**
 * An abstract class representing a card.
 */
public abstract class Card {

	public final ModifierSet modifiers = new ModifierSet();

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
			cards.add(new Zombie(5));
			cards.add(new Zombie(4));
			cards.add(new Zombie(3));
			cards.add(new Zombie(2));
			cards.add(new Zombie(5));
			cards.add(new Zombie(4));
			cards.add(new Zombie(3));
			cards.add(new Zombie(2));
			cards.add(new Hunger());
			cards.add(new Mass());
			cards.add(new Meat());
			cards.add(new Claws());
			cards.add(new Terror());
		} else {
			cards.add(new Gasoline());
			cards.add(new Gasoline());
			cards.add(new GetOut());
			cards.add(new GetOut());
			cards.add(new Burst(3));
			cards.add(new Burst(2));
			cards.add(new HandGrenade());
			cards.add(new HandGrenade());
			cards.add(new Shot(1));
			cards.add(new Shot(2));
			cards.add(new Sniper());
			cards.add(new Searchlight());
			cards.add(new HighVoltage());
			cards.add(new StreetOnFire());
		}
		return cards;
	}

	public abstract int rateSelection(GameState gameState, Selection selection);

	public abstract void makeEffect(Selection selection, GameState gameState);

	public abstract String getName();

	public abstract SelectionType getSelectionType();

	public abstract Integer getStrength();

	public abstract void setStrength(Integer strength);

}
