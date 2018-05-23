package probadoresAntiLucas;

import java.util.Scanner;

import cs.Cliente;

class chat_Probador {
	static Cliente cliente;

	static Thread hilo = new Thread() {
		public void run() {
			while (true) {
				try {
					System.out.println(cliente.recibir());
				} catch (Exception e) {
				}
			}
		}
	};

	public static void main(String a[]) throws Exception {
		cliente = new Cliente("localhost", 5050);
		hilo.start();

		@SuppressWarnings("resource")
		Scanner consola = new Scanner(System.in);
		cliente.nombre(consola.nextLine());
		while (true)
			try {
				
				String nextLine = consola.nextLine();  
				cliente.enviar(nextLine);
			} catch (Exception e) {
				System.out.println("error");
			}
	}
}