package model;

import java.util.ArrayList;
import java.util.List;

import cards.humans.Burst;
import cards.humans.Gasoline;
import cards.humans.GetOut;
import cards.humans.HandGrenade;
import cards.humans.Searchlight;
import cards.humans.Shot;
import cards.humans.Sniper;
import cards.zombies.Hunger;
import cards.zombies.Mass;
import cards.zombies.Meat;
import cards.zombies.Claws;
import cards.zombies.Zombie;
import controller.Selection;
import controller.Selection.SelectionType;

/**
 * An abstract class representing a card.
 */
public abstract class Card {

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
		}
		return cards;
	}

	public abstract boolean isSelectionCorrect(GameState gameState,
			Selection selection);

	public abstract void makeEffect(Selection selection, GameState gameState);

	public abstract String getName();

	public abstract SelectionType getSelectionType();

	public abstract Integer getStrength();

	public abstract void setStrength(Integer strength);

}
