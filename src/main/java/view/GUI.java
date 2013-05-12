package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Player;

public class GUI {
	public final EventReceiver eventReceiver;
	private Hand zombieHand;
	private Hand humanHand;
	private Board board;
	private JButton applyButton;
	private JButton cancelButton;
	private JFrame frame;
	
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

	public Hand getHand(Player player){
		if(player == Player.HUMAN)
			return humanHand;
		else
			return zombieHand;
	}
	
	public Board getBoard(){
		return board;
	}
	
	public void addApplyButtonMouseListener(MouseListener a){
	    applyButton.addMouseListener(a);
	}

	public void addCancelButtonMouseListener(MouseListener a){
	    cancelButton.addMouseListener(a);
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

		JPanel rightPanel = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridheight = 3;
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(rightPanel, gbc);

		frame.setVisible(true);

		humanHand = new Hand(humanHandPanel);
		zombieHand = new Hand(zombieHandPanel);
		board = new Board(boardPanel);

		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		rightPanel.add(applyButton = new JButton("Apply"));
		rightPanel.add(cancelButton = new JButton("Cancel"));

		frame.pack();

	}
	
	public void exit(){
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
