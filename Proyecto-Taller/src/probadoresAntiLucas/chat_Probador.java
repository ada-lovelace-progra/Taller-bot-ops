package probadoresAntiLucas;

import java.util.Scanner;

import usuariosYAsistente.Usuario;
import usuariosYAsistente.UsuarioGenerico;


class chat_Probador {

	static UsuarioGenerico alguien = new Usuario("fede.markoo");
	static Thread thread = new Thread() {
		public void run() {
			String viejo = "";
			while (true) {
				String recibirMensaje = alguien.recibirMensaje();
				if (!recibirMensaje.equals(viejo)) {
					System.out.println(recibirMensaje);
					viejo = recibirMensaje;
				}
			}
		}
	};

	public static void main(String a[]) {
		Scanner entrada = new Scanner(System.in);
		boolean a1 = true;

		thread.start();
		while (a1) {
			String mensaje = entrada.nextLine();
			alguien.enviarMensaje(mensaje);
		}
		entrada.close();
	}
}