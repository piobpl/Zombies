package utility;

import view.GUIController.Handler;

public class Courier<DataType> implements Handler<DataType> {
	// TODO
	public synchronized void call(DataType data) {
	}

	public synchronized DataType receive() {
		return null;
	}
}
