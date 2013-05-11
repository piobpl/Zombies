package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {
	public final EventReceiver eventReceiver;
	
	public GUI() {
		System.err.println("Creating GUI...");
		eventReceiver = new EventReceiver(this);
		try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					createWindow();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	void createWindow(){
		JFrame frame = new JFrame("Zombiaki");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridBagLayout());
		
		GridBagConstraints gbc;

		JPanel humanHand = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(humanHand, gbc);
		
		JPanel zombieHand = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(zombieHand, gbc);
		
		JPanel board = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(board, gbc);
		
		JPanel rightPanel = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridheight = 3;
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.getContentPane().add(rightPanel, gbc);
		
		frame.setVisible(true);
		
		JPanel cellPanel;
		
		humanHand.setLayout(new GridLayout(1, 4, 5, 5));
		for(int i = 0; i < 4; ++i){
			cellPanel = new JPanel();
			new Cell(cellPanel);
			humanHand.add(cellPanel);
		}
		
		zombieHand.setLayout(new GridLayout(1, 4, 5, 5));
		for(int i = 0; i < 4; ++i){
			cellPanel = new JPanel();
			new Cell(cellPanel);
			zombieHand.add(cellPanel);
		}
		
		board.setLayout(new GridLayout(5, 3, 5, 5));
		for(int i = 0; i < 15; ++i){
			cellPanel = new JPanel();
			new Cell(cellPanel);
			board.add(cellPanel);
		}

		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		rightPanel.add(new JButton("Apply"));
		rightPanel.add(new JButton("Cancel"));
		
		frame.pack();
		
	}
}
