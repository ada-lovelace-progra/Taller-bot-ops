package lucasTests;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class RF03Tests {

	public final static String USUARIO = "delucas";
	public final static Date FECHA_HORA = new GregorianCalendar(2018, 3, 1, 15, 15, 0).getTime();
	static Asistente ada;

	@BeforeClass
	public static void setup() {
		ada = new Asistente();
		escuchar("Hola @Ada");
	}

	private static String escuchar(String mensaje) {
		return ada.escuchar(USUARIO + ": " + mensaje).substring(4);
	}
	
	@Test
	public void hora() {
		String[] mensajes = { "¿qué hora es, @ada?", "@ada, la hora por favor", "me decís la hora @ada?" };
		for (String mensaje : mensajes) {
			Assert.assertEquals("@delucas son las 3:15 PM", escuchar(mensaje));
		}
	}

	@Test
	public void fecha() {
		String[] mensajes = { "¿qué día es, @ada?", "@ada, la fecha por favor", "me decís la fecha @ada?" };
		for (String mensaje : mensajes) {
			Assert.assertEquals("@delucas hoy es 1 de abril de 2018", escuchar(mensaje));
		}
	}

	@Test
	public void diaDeLaSemana() {
		String[] mensajes = { "¿qué día de la semana es hoy, @ada?" };
		for (String mensaje : mensajes) {
			Assert.assertEquals("@delucas hoy es domingo", escuchar(mensaje));
		}
	}

}
