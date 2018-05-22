package cs;

import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	private Socket servidor;
	private ServerSocket serversock;

	public Servidor(int puerto) throws Exception {
		serversock = new ServerSocket(puerto);
		while (true) {
			servidor = serversock.accept();
			new Hilo(servidor).start();
		}
	}
}
