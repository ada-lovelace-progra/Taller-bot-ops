package probadoresAntiLucas;

import java.util.Scanner;
import usuariosYAsistente.Usuario;

class chat_Probador {
	static Usuario usuario;

	static Thread hilo = new Thread() {
		public void run() {
			while (true)
				try {
					System.out.println(usuario.recibir());
				} catch (Exception e) {
					System.out.println("error rebiciendo");
					return;
				}
		}
	};

	public static void main(String a[]) throws Exception {

		@SuppressWarnings("resource")
		Scanner consola = new Scanner(System.in);

		String nombre = consola.nextLine();
		int codChat = Integer.parseInt(consola.nextLine());
		usuario = new Usuario(nombre);
		usuario.nuevoChat(codChat);
		String nextLine;
		hilo.start();

		while (true) {
			nextLine = consola.nextLine();
			usuario.enviar(codChat, nextLine);
		}
	}
}