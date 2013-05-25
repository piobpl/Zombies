package game.controller;

import game.model.Player;

public interface Stage {
	public void perform(Player player);
	public StageType getStageType();

	public enum StageType {
		ADVANCING, DISCARDING, DRAWING, PLAYING;
	}

}
