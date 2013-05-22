package model;

import game.model.GameState;

public abstract class TestGameStateCreator {
	public static GameState giveMeGameState() {
		return new GUILessGameState();
	}
}
