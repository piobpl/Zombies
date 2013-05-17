package model;

public abstract class TestGameStateCreator {
	public static GameState giveMeGameState() {
		return new GUILessGameState();
	}
}
