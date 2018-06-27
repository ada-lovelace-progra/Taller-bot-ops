package tests;

import java.util.ArrayList;

import org.junit.*;
import usuariosYAsistente.Asistente;

public class SaludosTest {

	public final static String USUARIO = "delucas";
	static Asistente ada;

	@BeforeClass
	public static void setup() {
	}

	private static String escuchar(String mensaje) {
		return ada.escuchar(USUARIO + ": " + mensaje).substring(4);
	}

	@Test
	public void llamadas() {
		String[] mensajes = { "@ada, necesito ayuda", "buenas @ada", "dale @ada, activa ameo" };
		ArrayList<String> respuestas = new ArrayList<String>();
		respuestas.add("Ada: Hola @" + USUARIO);
		respuestas.add("Ada: hola.... @" + USUARIO);
		respuestas.add("Ada: Buenos dias. En que puedo servirte @" + USUARIO);
		respuestas.add("Ada: Buenos dias!! @" + USUARIO);
		respuestas.add("Ada: en que puedo ayudarte @" + USUARIO);
		respuestas.add("Ada: buenos dias @" + USUARIO);
		respuestas.add("Ada: estoy para servirle @" + USUARIO);
		respuestas.add("Ada: mas vale que valga la pena... estaba durmiendo... @" + USUARIO);
		respuestas.add("Ada: mas vale que valga la pena... @" + USUARIO);

		for (String mensaje : mensajes) {
			ada = new Asistente();
			String escuchar = escuchar(mensaje);
			System.out.println(escuchar);
			Assert.assertTrue(respuestas.contains(escuchar));
			ada = null;
		}
	}

}
