package tests;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class RecordarEventosTest {

	public final static String USUARIO = "delucas";
///////////NO ME ANDA EL MALDITO JUNIT EL TEST  DE ESTE ESTA EN AGRADECER
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
	public void PruebaEvento() {
		//el archivo donde agrega los eventos esta en la carpeta Respuestas/respuestas_RecordarEventos.dat
		Assert.assertEquals("Ada: Evento agregado @delucas", escucha("@ada me agregas este evento : Fiesta en lo de pepe 2/08/2018 "));
		Assert.assertEquals("Ada: El proximo evento es (pinponeada en lo de facu 3/06/2018) y falta/n  (1) dias @delucas", escucha("@ada cual es mi proximo evento?"));
	}
	

}
