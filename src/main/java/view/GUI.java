package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Player;

public class GUI {
	public static enum Button {
		ApplySelection, CancelSelection, EndTurn;
	}

	public final EventReceiver eventReceiver;
	private Hand zombieHand;
	private Hand humanHand;
	private Board board;
	private JButton applySelectionButton;
	private JButton cancelSelectionButton;
	private JButton endTurnButton;
	private JFrame frame;
	private JLabel zombieCardsLeft;
	private JLabel humanCardsLeft;

	public GUI() {
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
		eventReceiver = new EventReceiver(this);
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

	public void addButtonMouseListener(Button button, MouseListener a) {
		switch (button) {
		case ApplySelection:
			applySelectionButton.addMouseListener(a);
			break;
		case CancelSelection:
			cancelSelectionButton.addMouseListener(a);
			break;
		case EndTurn:
			endTurnButton.addMouseListener(a);
			break;
		default:
			throw new UnsupportedOperationException();
		}
	}

	public void setCardsLeft(Player player, int left) {
		if (Player.ZOMBIE == player) {
			zombieCardsLeft.setText("" + left + " cards left");
		} else {
			humanCardsLeft.setText("" + left + " cards left");
		}
	}

	private void createWindow() {
		frame = new JFrame("Zombiaki");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridBagLayout());

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
		frame.getContentPane().add(boardPanel, gbc);

		zombieCardsLeft = new JLabel();
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(zombieCardsLeft, gbc);

		JPanel rightPanel = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(rightPanel, gbc);

		humanCardsLeft = new JLabel();
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(humanCardsLeft, gbc);

		frame.setVisible(true);

		humanHand = new Hand(humanHandPanel);
		zombieHand = new Hand(zombieHandPanel);
		board = new Board(boardPanel);

		rightPanel.setLayout(new FlowLayout());
		rightPanel.setPreferredSize(new Dimension(120, 300));

		applySelectionButton = new JButton("Apply Selection");
		applySelectionButton.setPreferredSize(new Dimension(120, 30));
		rightPanel.add(applySelectionButton);

		cancelSelectionButton = new JButton("Cancel Selection");
		cancelSelectionButton.setPreferredSize(new Dimension(120, 30));
		rightPanel.add(cancelSelectionButton);

		endTurnButton = new JButton("End turn");
		endTurnButton.setPreferredSize(new Dimension(120, 30));
		rightPanel.add(endTurnButton);

		frame.pack();

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
