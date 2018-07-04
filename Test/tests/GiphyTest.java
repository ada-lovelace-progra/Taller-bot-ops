package tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class GiphyTest {

	public final static String USUARIO = "delucas";

	static Asistente ada;

	@BeforeClass
	public static void setup() {
		ada = new Asistente();
		escucha("Hola @Ada");
	}

	private static String escucha(String mensaje) {
		return ada.escuchar(USUARIO + ": " + mensaje).substring(4);
	}

	@Test
	public void giphy() {
		String[] mensajes = { "@ada gif de perros", "@ada gif de algo", "@ada giphy de fiesta" };
		for (String mensaje : mensajes) {
			Assert.assertTrue(escucha(mensaje).contains("<img"));
		}
	}

}
