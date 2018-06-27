package tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class ClimaTest {

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
	public void clima() {
		String[] mensajes = { "@ada clima", "@ada me decis el clima", "@ada como esta el clima" };
		for (String mensaje : mensajes) {
			Assert.assertTrue(escucha(mensaje).contains("Humedad"));
		}
	}

}
