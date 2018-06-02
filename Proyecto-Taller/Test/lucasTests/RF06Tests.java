package lucasTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class RF06Tests {

	public final static String USUARIO = "delucas";

	Asistente ada;

	@Before
	public void setup() {
		ada = new Asistente();
	}

	@Test
	public void calculos() {
	//	Assert.assertEquals("@delucas 3", ada.escuchar("@ada cuánto es 1 + 2"));

	//	Assert.assertEquals("@delucas 1", ada.escuchar("@ada cuánto es 5 - 2 * 2"));

	//	Assert.assertEquals("@delucas 10", ada.escuchar("@ada cuánto es el 10% de 100"));

	//	Assert.assertEquals("@delucas 42", ada.escuchar("@ada cuánto es el 17 + 5 ^ 2"));

		// agregar otros casos
	}

	@Test
	public void calculosCompuestos() {
	//	Assert.assertEquals("@delucas -6", ada.escuchar("@ada cuánto es (4-8)*2 + 4 / ( 1 + 1)"));

		// agregar otros casos
	}

}
