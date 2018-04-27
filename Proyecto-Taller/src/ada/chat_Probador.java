package ada;
import java.util.Scanner;

class chat_Probador {
	public static void main(String a[]) {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		asistente Asistent = new asistente();
		while(true) {
				String mensaje = entrada.nextLine();
				if(Asistent.activo)
					System.out.println(Asistent.procesarMensaje(mensaje));
				else
					if(Asistent.seLlamoAlAsistente(mensaje))
						System.out.println("Buenos dias");
		}
	}
}