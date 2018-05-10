package probadoresAntiLucas;

import sockets.Servidor;

public class serverTesteador {

	static public void main(String a[]) {
		Servidor server = new Servidor();
		server.Conectar(5050);
		while (true) {
			String recibir = server.recibir();
			System.out.println(recibir);
		}
	}
}
