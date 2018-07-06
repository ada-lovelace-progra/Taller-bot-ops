package cs;

import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	private Socket servidor;
	private ServerSocket serversock;

	public Servidor(int puerto) throws Exception {
		serversock = new ServerSocket(puerto);
		//new dbUsuarios.BaseDato();
		while (true) {
			servidor = serversock.accept();
			new HiloServer(servidor).start();
		}
	}
}