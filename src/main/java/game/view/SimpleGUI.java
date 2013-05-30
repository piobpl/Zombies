package game.view;

import game.model.Modifier;
import game.model.Player;
import game.view.EventReceiver.TriggerEventHandler;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class SimpleGUI implements GUI {
	private EventReceiver eventReceiver;
	private Hand zombieHand;
	private Hand humanHand;
	private Board board;
	private JPanel glass;
	private JFrame frame;
	private JLabel zombieCardsLeft;
	private JLabel humanCardsLeft;
	private JPanel rightPanel;
	private InfoPanel infoPanel;
	private HistoryPanel historyPanel;

	public SimpleGUI(TriggerEventHandler triggerEventHandler) {
		System.err.println("Creating GUI...");
		try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					createWindow();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
		eventReceiver = new EventReceiver(this, triggerEventHandler);
	}

	public void setButtonEnabled(Button button, boolean aktywny) {
		infoPanel.setButtonEnabled(button, aktywny);
	}

	public Hand getHand(Player player) {
		if (player == Player.HUMAN)
			return humanHand;
		else
			return zombieHand;
	}

	public Board getBoard() {
		return board;
	}

	public EventReceiver getEventReceiver() {
		return eventReceiver;
	}

	public void addButtonMouseListener(Button button, MouseListener a) {
		if (button == Button.Save) {
			historyPanel.addButtonMouseListener(button, a);
		} else
			infoPanel.addButtonMouseListener(button, a);
	}

	public void setCardsLeft(Player player, int left) {
		if (Player.ZOMBIE == player) {
			zombieCardsLeft.setText("" + left + " cards left");
		} else {
			humanCardsLeft.setText("" + left + " cards left");
		}
	}

	public void modelSendsMessage(final String message) {
		infoPanel.sendMessage(message);
	}

	public void modelSendsAllMessages(final List<String> messages) {
		infoPanel.sendAllMessages(messages);
	}

	public void drawGlobalModifiers(final Iterable<Modifier> modifiers) {
		infoPanel.drawGlobalModifiers(modifiers);
	}

	private void createWindow() {
		ToolTipManager.sharedInstance().setDismissDelay(60000);
		frame = new JFrame("Zombiaki");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridBagLayout());
		frame.getContentPane().setBackground(Colors.tlo.getColor());
		frame.setResizable(false);

		glass = new JPanel();
		glass.setLayout(null);
		frame.setGlassPane(glass);
		glass.setVisible(true);
		glass.setOpaque(false);

		GridBagConstraints gbc;

		JPanel humanHandPanel = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(humanHandPanel, gbc);

		JPanel zombieHandPanel = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(zombieHandPanel, gbc);

		JPanel boardPanel = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 10, 10, 10);
		boardPanel.setBackground(Colors.tlo.getColor());
		frame.getContentPane().add(boardPanel, gbc);

		zombieCardsLeft = new JLabel();
		zombieCardsLeft.setForeground(Colors.margines.getColor());
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(zombieCardsLeft, gbc);

		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(rightPanel, gbc);

		humanCardsLeft = new JLabel();
		humanCardsLeft.setForeground(Colors.margines.getColor());
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(humanCardsLeft, gbc);

		frame.setVisible(true);

		rightPanel.setBackground(Colors.boardsCard.getColor());
		rightPanel.setForeground(Colors.boardsCard.getColor());

		JPanel panel;
		rightPanel.add(panel = new JPanel());
		panel.setOpaque(false);
		infoPanel = new InfoPanel(panel);
		rightPanel.add(panel = new JPanel());
		panel.setOpaque(false);
		historyPanel = new HistoryPanel(panel);

		humanHand = new SimpleHand(humanHandPanel, Colors.humansCard.getColor(),
				Colors.tlo.getColor());
		zombieHand = new SimpleHand(zombieHandPanel, Colors.zombieCard.getColor(),
				Colors.tlo.getColor());
		board = new SimpleBoard(boardPanel, Colors.boardsCard.getColor(),
				Colors.boardsCard.getColor());

		frame.pack();

		board.registerToGlass(glass);

	}

	public void setHighlight(boolean set) {
		board.setHighlight(set);
		zombieHand.setHighlight(set);
		humanHand.setHighlight(set);
	}

	public void exit() {
		try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					frame.dispose();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
