package lobby.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import lobby.view.Listener.Receiver;
import server.controller.Message;
import server.controller.Message.ChatMessage;
import server.controller.Message.LoginMessage;

public class Lobby {

	private JFrame frame;
	public final String login;
	private JTextArea chat;
	private Listener listener;

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
		try {
			listener = new Listener(new Receiver() {
				public void receive(Message message) {
					switch (message.getType()) {
					case CHAT:
						ChatMessage cmsg = (ChatMessage) message;
						chat.append(cmsg.from + " wrote: " + cmsg.message
								+ "\n");
						break;
					case ERROR:
						break;
					case LOGIN:
						LoginMessage lmsg = (LoginMessage) message;
						chat.append(lmsg.login + " has logged in.\n");
						break;
					default:
						break;
					}
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
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		Container content = frame.getContentPane();

		content.setLayout(new FlowLayout());
		content.setPreferredSize(new Dimension(500, 500));

		chat = new JTextArea(30, 40);
		chat.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(chat);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		content.add(scrollPane);

		final JTextField text = new JTextField(40);
		content.add(text);
		text.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listener.send(new ChatMessage(login, text.getText()));
				text.setText("");
			}
		});

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				listener.close();
			}
		});

		frame.pack();
	}
}
