package cs;

import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	private Socket servidor;
	private ServerSocket serversock;

	public Servidor(int puerto) {
		try {
			serversock = new ServerSocket(puerto);
			while (true) {
				servidor = serversock.accept();
				asd();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void asd() {
		new Hilo(servidor).start();
	}
}
