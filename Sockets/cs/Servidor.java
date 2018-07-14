package cs;

import java.io.IOException;
import java.net.ServerSocket;

public class Servidor {
	private ServerSocket serversock;

	public Servidor() {
		try {
			new dbUsuarios.BaseDato();
			new bdRespuestas.BaseDato();
			new bdRespuestas.EventosBD();
			new bdRespuestas.DeudasBD();
			serversock = new ServerSocket(5050);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				new HiloServer(serversock.accept()).start();
			} catch (Exception e) {
			}
		}
	}

	public static void main(String a[]) {
		new Servidor();
	}
}