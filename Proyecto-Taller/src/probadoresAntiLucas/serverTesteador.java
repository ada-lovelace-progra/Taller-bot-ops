package probadoresAntiLucas;

import sockets.Servidor;

public class serverTesteador {

	static Servidor server = new Servidor();

	static public void main(String a[]) {
		server.Conectar(5050);
		while(true)
		{
			String recibir = server.recibir();
			System.out.println(recibir);
			server.enviar(recibir);
		}
	}
}
