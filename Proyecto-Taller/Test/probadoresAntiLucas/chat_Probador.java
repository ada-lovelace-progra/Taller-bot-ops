package probadoresAntiLucas;

import java.util.Scanner;

import cs.Cliente;

class chat_Probador {
	public static void main(String a[]) throws Exception {
		Cliente cliente = new Cliente("localhost", 5050);
		@SuppressWarnings("resource")
		Scanner asd = new Scanner(System.in);
		new Thread() {
			public void run() {
				while (true) {
					try {
						System.out.println(cliente.recibir());
					} catch (Exception e) {
					}
				}
			}
		}.start();
		while (true)
			try {
				cliente.enviar(asd.nextLine());
			} catch (Exception e) {
			}
	}
}