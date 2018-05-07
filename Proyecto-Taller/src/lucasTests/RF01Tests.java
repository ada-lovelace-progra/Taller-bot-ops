package lucasTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class RF01Tests {

	public final static String USUARIO = "delucas";

	Asistente ada;

	@Before
	public void setup() {
		ada = new Asistente();
	}

	@Test
	public void saludo() {
		String[] mensajes = { "¡Hola, @ada!", "@ada hola!", "buen día @ada", "@ada, buenas tardes", "hey @ada" };
		for (String mensaje : mensajes) {
		//	Assert.assertEquals("¡Hola, @delucas!", ada.escuchar(mensaje));
		}
	}

}
