package tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import plugins.Codificaciones;
import usuariosYAsistente.Asistente;

public class Gag9Test {

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
	public void gag9() {
		String[] mensajes = { "@ada 9gag", "@ada imagen de 9gag" };
		for (String mensaje : mensajes) {
			Assert.assertTrue(escucha(mensaje).contains("<img"));
		}
	}

}
