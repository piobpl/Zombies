package game.view;

import game.model.Modifier;
import game.model.Player;
import game.view.EventReceiver.TriggerEventHandler;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.event.ChangeListener;

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
	private HistoryPanel history;
	private JPanel historyPanel;
	private JButton commandButton;

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
		eventReceiver = new SimpleEventReceiver(this, triggerEventHandler);
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

	public void setButtonEnabled(Button button, final boolean active) {
		switch (button) {
		case Save:
			history.setButtonEnabled(button, active);
			break;
		case Command:
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					commandButton.setEnabled(active);
				}
			});
			break;
		default:
			infoPanel.setButtonEnabled(button, active);
			break;
		}
	}

	public void addButtonListener(Button button, final ActionListener a) {
		switch (button) {
		case Save:
			history.addButtonListener(button, a);
			break;
		case Command:
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					commandButton.addActionListener(a);
				}
			});
			break;
		default:
			infoPanel.addButtonListener(button, a);
			break;
		}
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

	public void modelSetsAllMessages(final List<String> messages) {// nowa
																	// funkcja
		infoPanel.setAllMessages(messages);
	}

	public void drawGlobalModifiers(final Iterable<Modifier> modifiers) {
		infoPanel.drawGlobalModifiers(modifiers);
	}

	private void createWindow() {
		JPanel panel;

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

		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setOpaque(false);
		panel.setAlignmentY(Component.LEFT_ALIGNMENT);
		panel.setPreferredSize(new Dimension(400, 50));
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(panel, gbc);
		zombieCardsLeft = new JLabel();
		zombieCardsLeft.setForeground(Colors.margines.getColor());
		zombieCardsLeft.setPreferredSize(new Dimension(120, 30));
		panel.add(zombieCardsLeft);
		commandButton = new JButton("Command");
		commandButton.setPreferredSize(new Dimension(120, 30));
		panel.add(commandButton);

		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(rightPanel, gbc);

		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setOpaque(false);
		panel.setAlignmentY(Component.LEFT_ALIGNMENT);
		panel.setPreferredSize(new Dimension(400, 50));
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(panel, gbc);
		humanCardsLeft = new JLabel();
		humanCardsLeft.setForeground(Colors.margines.getColor());
		humanCardsLeft.setPreferredSize(new Dimension(220, 30));
		panel.add(humanCardsLeft);
		JButton helpButton = new JButton("Help");
		helpButton.setPreferredSize(new Dimension(120, 30));
		panel.add(helpButton);
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHelp();
			}
		});

		frame.setVisible(true);

		rightPanel.setBackground(Colors.boardsCard.getColor());
		rightPanel.setForeground(Colors.boardsCard.getColor());

		rightPanel.add(panel = new JPanel());
		panel.setOpaque(false);
		infoPanel = new InfoPanel(panel);
		rightPanel.add(historyPanel = new JPanel());
		historyPanel.setOpaque(false);
		history = new HistoryPanel(historyPanel);

		humanHand = new SimpleHand(humanHandPanel,
				Colors.humansCard.getColor(), Colors.tlo.getColor());
		zombieHand = new SimpleHand(zombieHandPanel,
				Colors.zombieCard.getColor(), Colors.tlo.getColor());
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

	public void setPlayer(Player player) {
	}

	@Override
	public void drawHistorySlider(int turn) {
		history.setSliderRange(turn);
	}

	@Override
	public void addSliderChangeListener(final ChangeListener cl) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				history.addSliderChangeListener(cl);
			}
		});
	}

	private void showHelp() {
		JOptionPane.showMessageDialog(null, new JLabel(Help.helpText), "Help", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void hideHistoryPanel(){
		rightPanel.remove(historyPanel);
		frame.pack();
	}

}
