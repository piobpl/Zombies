package lobby.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import server.controller.Message;
import server.controller.Message.ChatMessage;
import server.controller.Message.InviteMessage;
import server.controller.Message.LoginMessage;
import server.controller.Message.LogoutMessage;
import server.controller.Message.PlayerListMessage;
import utility.Listener;
import utility.Listener.Receiver;

public class Lobby {

	private JFrame frame;
	public final String login;
	private JTextArea chat;
	private Listener listener;
	private JList<String> playersList;
	private DefaultListModel<String> listModel;
	private String invitedPlayer;
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
		invitedPlayer = null;
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
					case INVITEMESSAGE:
						// TODO + dodanie obslugi odpowiedzi i startu gry
						String whoInvites = ((InviteMessage) message).whoInvites;
						System.err.println("Player: " + whoInvites
								+ " invites You!");
						JOptionPane.showMessageDialog(frame, "Player: "
								+ whoInvites + " invites You!");
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
		frame.setVisible(true);

		Container content = frame.getContentPane();

		content.setLayout(new GridBagLayout());
		content.setPreferredSize(new Dimension(600, 500));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		chat = new JTextArea(30, 40);
		chat.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(chat);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		c.gridx = 0;
		c.gridy = 0;
		content.add(scrollPane, c);

		listModel = new DefaultListModel<String>();
		playersList = new JList<String>(listModel);
		playersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel listSelectionModel = playersList.getSelectionModel();
		listSelectionModel
				.addListSelectionListener(new PlayersListSelectionHandler());
		c.gridx = 1;
		c.gridy = 0;
		content.add(playersList, c);

		final JTextField text = new JTextField(40);
		c.gridx = 0;
		c.gridy = 1;
		content.add(text, c);
		text.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listener.send(new ChatMessage(login, text.getText()));
				text.setText("");
			}
		});

		inviteButton = new JButton("Invite");
		c.gridx = 1;
		c.gridy = 1;
		inviteButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (invitedPlayer != null) {
					listener.send(new InviteMessage(login, invitedPlayer));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

		});
		content.add(inviteButton, c);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				listener.close();
			}
		});

		frame.pack();
	}

	class PlayersListSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			boolean isAdjusting = e.getValueIsAdjusting();
			if (isAdjusting) {
				if (lsm.isSelectionEmpty()) {
					invitedPlayer = null;
				} else {
					int index = lsm.getMinSelectionIndex();
					invitedPlayer = listModel.elementAt(index);
				}
			}
		}
	}

	void updatePlayers() {
		listModel.clear();
		for (String s : players) {
			listModel.addElement(s);
		}
	}
}
