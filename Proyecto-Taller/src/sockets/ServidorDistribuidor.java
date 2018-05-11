package sockets;

import java.util.ArrayList;
import java.util.Hashtable;

public class ServidorDistribuidor {
	static Hashtable<Integer, Hashtable<String, ArrayList<String>>> chatsyusuarios = new Hashtable<Integer, Hashtable<String, ArrayList<String>>>();
	// aca lo que pensaba hacer es guardar los mensajes para poder hacer multichat
	// y en la segunda hash filtro por nombre de usuario...
	static int conectados = 0;

	public static void main(String a[]) {
		while (true) {
			Servidor server = new Servidor();
			server.Conectar(5050 + conectados);
			conectados++;
			new Thread() {
				public void run() {
					Hashtable<String, ArrayList<String>> tablaPorUsuario;
					while (true) {
						String Mensaje = server.recibir();
						String Usuario = Mensaje.substring(4, Mensaje.indexOf(":"));
						int codChat = Integer.parseInt(Mensaje.substring(0, 4));
						if (!chatsyusuarios.containsKey(codChat))
							chatsyusuarios.put(codChat, new Hashtable<String, ArrayList<String>>());

						tablaPorUsuario = chatsyusuarios.get(codChat);
						if (!tablaPorUsuario.containsKey(Usuario))
							tablaPorUsuario.put(Usuario, new ArrayList<String>());

						tablaPorUsuario.get(Usuario).add(Mensaje);
					}
				}
			}.start();
		}
	}

}
