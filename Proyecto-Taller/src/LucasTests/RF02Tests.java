package LucasTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ada.Asistente;

public class RF02Tests {

	public final static String USUARIO = "delucas"; 
	
	Asistente ada;
	
	@Before
	public void setup() {
		ada = new Asistente();
	}
	
	@Test
	public void agradecimiento() {
		String[] mensajes = {
				"¡Muchas gracias, @ada!",
				"@ada gracias",
				"gracias @ada"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"No es nada, @delucas",
					ada.escuchar(mensaje)
			);
		}
	}
	
}
