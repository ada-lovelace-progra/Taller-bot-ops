package tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class RecordarEventosTest {

	public final static String USUARIO = "delucas";
	static Asistente ada;

	@BeforeClass
	public static void setup() {
		ada = new Asistente();
		escucha("Hola @Ada");
	}

	private static String escucha(String mensaje) {
		String escuchar = ada.escuchar(USUARIO + ": " + mensaje);
		if (escuchar.length() > 5)
			return escuchar.substring(4);
		return null;
	}

	@Test
	public void leerEvento() {
		ArrayList<String> rep = new ArrayList<>();
		String[] cad = { "Ada: El proximo evento es (pinponeada en lo de facu 3/06/2018) y falta/n  (1) dias @delucas",
				"Ada: No hay eventos para el dia de la fecha @delucas" };
	
		for (String temp : cad)
			rep.add(temp);
		
		String escucha = escucha("@ada cual es mi proximo evento?");
		Assert.assertTrue(rep.contains(escucha));
	}

	@Test
	public void cargarEvento() {
		Assert.assertEquals("Ada: Evento agregado @delucas",
				escucha("@ada me agregas este evento : Fiesta en lo de pepe 2/08/2018 "));
	}

}
