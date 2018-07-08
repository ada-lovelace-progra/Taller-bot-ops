package cs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	private Socket servidor;
	private ServerSocket serversock;

	public Servidor(int puerto) {
		try {
			serversock = new ServerSocket(puerto);
		} catch (IOException e1) {
		}
		while (true) {
			try {
				servidor = serversock.accept();
				new HiloServer(servidor).start();
			} catch (Exception e) {
			}
		}
	}

	public Servidor() {
		this(5050);
	}

	public static void main(String a[]) {
		new Servidor();
	}
}