package lucasTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class RF10Tests {

	public final static String USUARIO = "delucas";
	public final static int ELEGIDO = 12;

	Asistente ada;

	@Before
	public void setup() {
		ada = new Asistente();
	}

	@Test
	public void adivinando() {
//		Assert.assertEquals("@delucas ¡sale y vale! Pensá un número del 1 al 100", ada.escuchar("@ada jugamos?"));

//		Assert.assertEquals("@delucas ¿es el 50?", ada.escuchar("@ada listo"));

//		Assert.assertEquals("@delucas ¿es el 75?", ada.escuchar("@ada más grande"));

//		Assert.assertEquals("@delucas ¿es el 62?", ada.escuchar("@ada más chico"));

//		Assert.assertEquals("@delucas ¿es el 68?", ada.escuchar("@ada más grande"));

//		Assert.assertEquals("@delucas fue divertido :)", ada.escuchar("@ada si!"));
	}

	@Test
	public void pensandoNumero() {
//		Assert.assertEquals("@delucas ¡listo!", ada.escuchar("@ada jugamos? Pensá un número del 1 al 100"));

//		Assert.assertEquals("@delucas más chico", ada.escuchar("@ada es el 50?"));

//		Assert.assertEquals("@delucas ¡si! Adivinaste en 2 pasos...", ada.escuchar("@ada es el 12?"));

	}

}
