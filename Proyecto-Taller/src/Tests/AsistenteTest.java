package Tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ada.Asistente;

public class AsistenteTest {

	public final static String USUARIO = "delucas";

	Asistente ada;

	@Before
	public void setup() {
		ada = new Asistente();
	}

	@Test
	public void sinsentido() {
		String[] mensajes = {
				"Este mensaje no tiene sentido @ada"
		};
		/// se debe inicar el asistente
		ada.escuchar("ada, necesito ayuda");
		
		for (String mensaje : mensajes) {
		/*	Assert.assertEquals(
			*		"Disculpa... no entiendo el pedido, @delucas ¿podrías repetirlo?",
			*		ada.escuchar(mensaje));*/
			Assert.assertEquals("como se realiza dicha peticion?", ada.escuchar(mensaje));
		}
	}

}
