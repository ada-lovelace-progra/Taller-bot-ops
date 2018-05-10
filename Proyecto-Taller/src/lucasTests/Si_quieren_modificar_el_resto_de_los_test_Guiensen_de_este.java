package lucasTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sockets.Servidor;
import usuariosYAsistente.Usuario;

public class Si_quieren_modificar_el_resto_de_los_test_Guiensen_de_este {

	public final static String USUARIO = "delucas";

	Usuario user = new Usuario(USUARIO);

	@BeforeClass
	public static void inicio() {
		new Thread() {
			public void run() {
				Servidor server = new Servidor();
				server.Conectar(5050);
				while (true) {
					@SuppressWarnings("unused")
					String recibir = server.recibir();
			//		System.out.println(recibir);
				}
			}
		}.start();
	}

	@Test
	public void saludo() {
		String[] mensajes = { "hola ada", "buenas ada" };// , "@ada hola!",
															// "buen día @ada",
		// "@ada, buenas tardes", "hey @ada"
		// };
		String respuetas = "Ada Lovelace: buenos dias @" + USUARIO 
				+ "Ada Lovelace: Buenos dias!! @" + USUARIO
				+ "Ada Lovelace: hola.... @" + USUARIO 
				+ "Ada Lovelace: Hola @" + USUARIO;
		for (String mensaje : mensajes) {
			user.enviarMensaje(mensaje);
			Assert.assertTrue(respuetas.contains(user.recibirMensaje()));
			user.enviarMensaje("chau ada");
			user.recibirMensaje();
		}
	}

}
