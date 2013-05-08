package view;

public class GUI {
	public final EventReceiver eventReceiver;

	public GUI() {
		System.err.println("Creating GUI...");
		eventReceiver = new EventReceiver(this);
	}

	// TODO

}
