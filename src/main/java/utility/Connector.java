package utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connector {

	public final InputStream in;
	public final OutputStream out;
	private Socket socket;

	private Connector(Socket socket, InputStream in, OutputStream out) {
		this.socket = socket;
		this.in = in;
		this.out = out;
	}

	public static Connector makeConnection(String host, int port)
			throws IOException {
		Socket socket = new Socket(host, port);
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		return new Connector(socket, in, out);
	}

	public void close() throws IOException {
		in.close();
		out.close();
		socket.close();
	}

}
