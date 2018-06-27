package tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import plugins.Codificaciones;
import usuariosYAsistente.Asistente;

public class MemesTest {

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
	public void Memes() {
		String[] mensajes = { ":its a trap:", ":yao ming:", ":troll face:" };
		for (String mensaje : mensajes) {
			Assert.assertTrue(Codificaciones.codificar(mensaje).contains("<img"));
		}
	}

}
