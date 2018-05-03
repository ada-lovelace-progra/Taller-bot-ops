package ada;

import java.util.Scanner;

class chat_Probador {
	public static void main(String a[]) {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		Asistente Asistent = new Asistente();
		while (true) {
			String mensaje = entrada.nextLine();
			System.out.println(Asistent.escuchar(mensaje));
		}
	}
}