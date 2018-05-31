package tests;

import java.util.ArrayList;

import org.junit.*;
import cs.Servidor;
import usuariosYAsistente.Usuario;

public class Asistente_SaludosTest {

	public final static String USUARIO = "delucas";
	static Usuario user;

	@BeforeClass
	public static void inicio() {
		new Thread() {
			public void run() {
				Servidor server = new Servidor();
				server.Conectar(5050);
				while (true) {
					String recibir = server.recibir();
					System.out.println(recibir);
				}
			}
		}.start();
		user = new Usuario(USUARIO);
	}

	@Test
	public void llamadas() {
		String[] mensajes = { "ada, necesito ayuda", "buenas ada", "dale ada, activa ameo" };
		ArrayList<String> respuestas = new ArrayList<String>();
		respuestas.add("Ada Lovelace: Hola @" + USUARIO);
		respuestas.add("Ada Lovelace: hola....@" + USUARIO);
		respuestas.add("Ada Lovelace: Buenos dias. En que puedo servirte @" + USUARIO);
		respuestas.add("Ada Lovelace: Buenos dias!! @" + USUARIO);
		respuestas.add("Ada Lovelace: en que puedo ayudarte @" + USUARIO);
		respuestas.add("Ada Lovelace: buenos dias @" + USUARIO);
		respuestas.add("Ada Lovelace: estoy para servirle @" + USUARIO);
		respuestas.add("Ada Lovelace: mas vale que valga la pena... estaba durmiendo... @" + USUARIO);
		respuestas.add("Ada Lovelace: mas vale que valga la pena... @" + USUARIO);

		for (String mensaje : mensajes) {
			user.enviarMensaje(mensaje);
			Assert.assertTrue(respuestas.contains(user.recibirMensaje()));
			user.enviarMensaje("chau ada");
			user.recibirMensaje();

		}
	}

}
