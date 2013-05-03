package view;

import utility.Pair;

public class GUIController {
	public final GUI gui;

	public GUIController(GUI gui) {
		this.gui = gui;
	}

	public static interface Handler<DataType> {
		public void call(DataType T);
	}

	// TODO

	public void setBoardClickedHandler(Handler<Pair<Integer, Integer>> handler) {
	}

	public void setHandClickedHandler(Handler<Integer> handler) {
	}

	public void setApplyButtonHandler(Handler<Void> handler) {
	}

	public void setCancelButtonHandler(Handler<Void> handler) {
	}

	public void resetBoardClickedHandler() {
	}

	public void resetHandClickedHandler() {
	}

	public void resetApplyButtonHandler() {
	}

	public void resetCancelButtonHandler() {
	}
}
