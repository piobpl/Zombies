package lobby.view;

import game.view.GUIProxy;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import server.controller.Message;
import server.controller.Message.ChatMessage;
import server.controller.Message.GameStartMessage;
import server.controller.Message.InviteMessage;
import server.controller.Message.LoginMessage;
import server.controller.Message.LogoutMessage;
import server.controller.Message.PlayerListMessage;
import utility.Listener;
import utility.Listener.Receiver;

public class Lobby {

	public final String login;
	private JFrame frame;
	private JTextArea chat;
	private Listener listener;
	private JList<String> playersList;
	private DefaultListModel<String> listModel;
	private JButton inviteButton;
	private List<String> players;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				final String login = (String) JOptionPane.showInputDialog(null,
						"Login:", "Log in", JOptionPane.PLAIN_MESSAGE, null,
						null, null);
				if (login != null) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							new Lobby(login);
						}
					}).start();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Lobby(String login) {
		this.login = login;
		players = new ArrayList<>();
		try {
			listener = new Listener(new Receiver() {
				public void receive(Listener listener, Message message) {
					switch (message.getType()) {
					case CHAT:
						ChatMessage cmsg = (ChatMessage) message;
						chat.append(cmsg.from + " wrote: " + cmsg.message
								+ "\n");
						break;
					case ERROR:
						break;
					case LOGIN:
						LoginMessage limsg = (LoginMessage) message;
						chat.append(limsg.login + " has logged in.\n");
						players.add(limsg.login);
						updatePlayers();
						break;
					case LOGOUT:
						LogoutMessage lomsg = (LogoutMessage) message;
						chat.append(lomsg.login + " disconnected.\n");
						players.remove(lomsg.login);
						updatePlayers();
						break;
					case INVITE:
						String whoInvites = ((InviteMessage) message).whoInvites;
						String[] options = { "Yes", "No, thanks" };
						int n = JOptionPane.showOptionDialog(frame,
								"Do you want to play with " + whoInvites + "?",
								"Game", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
						if (n == 0)
							listener.send(new GameStartMessage(whoInvites,
									Lobby.this.login));
						break;
					case GAMESTART:
						listener.pause();
						listener.removeReceiver(this);
						new Thread(new GUIProxy(listener, Lobby.this.login))
								.start();
						frame.dispose();
						break;
					case PLAYERLIST:
						players.clear();
						players.addAll(((PlayerListMessage) message).playerList);
						break;
					default:
						break;
					}
				}

				@Override
				public void unregister(Listener listener) {
					frame.dispose();
				}

			}, new Socket("localhost", 8888));
		} catch (IOException e) {
			e.printStackTrace();
		}
		listener.send(new LoginMessage(login));
		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					initialize();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(listener).start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Lobby");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		Container content = frame.getContentPane();

		content.setLayout(new GridBagLayout());

		GridBagConstraints gbc;

		chat = new JTextArea(30, 40);
		chat.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(chat);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 10);
		content.add(scrollPane, gbc);

		JPanel panel = new JPanel(new FlowLayout());
		panel.setPreferredSize(new Dimension(80, 450));

		JLabel label = new JLabel("Players:");
		panel.add(label, gbc);

		listModel = new DefaultListModel<String>();
		playersList = new JList<String>(listModel);
		playersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playersList.setPreferredSize(new Dimension(80, 424));
		panel.add(playersList);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 0, 10, 10);
		content.add(panel, gbc);

		final JTextField text = new JTextField(41);
		text.setPreferredSize(new Dimension(0, 24));
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (text.getText().length() == 40)
					e.consume();
			}
		});
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 10, 10, 10);
		content.add(text, gbc);
		text.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listener.send(new ChatMessage(login, text.getText()));
				text.setText("");
			}
		});

		inviteButton = new JButton("Invite");
		inviteButton.setPreferredSize(new Dimension(80, 24));
		inviteButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (playersList.getSelectedValue() != null
						&& !playersList.getSelectedValue().equals(login))
					listener.send(new InviteMessage(login, playersList
							.getSelectedValue()));
			}
		});
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 10, 10);
		content.add(inviteButton, gbc);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				listener.close();
			}
		});

		frame.pack();
	}

	void updatePlayers() {
		listModel.clear();
		for (String s : players) {
			listModel.addElement(s);
		}
	}
}
