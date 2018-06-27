package tests;

import org.junit.*;

import usuariosYAsistente.Asistente;

public class UnidadesTest {

	private static final String USUARIO = "ash";
	static Asistente ada;

	@BeforeClass
	public static void setup() {
		ada = new Asistente();
		ada.escuchar(USUARIO + ": " + "hola @ada");
	}

	private static String escuchar(String mensaje) {
		String escuchar = ada.escuchar(USUARIO + ": " + mensaje);
		if (escuchar.length() > 5) {
			String substring = escuchar.substring(4);
			System.out.println(substring);
			return substring;
		}
		return null;
	}

	@Test
	public void mismoSistema() {
		Assert.assertEquals("Ada: 1000.0 Gramo (gr) es equivalente a 1.0 Kilogramo (kg) @" + USUARIO,
				escuchar("@ada pasar 1000 gr a kg"));
	}

	@Test
	public void distintosSistemas() {
		Assert.assertEquals("Ada: No se pueden convertir unidades de gr a pt. @" + USUARIO,
				escuchar("@ada converti 1000 gr a pt"));
	}

	public void tiempo() {
		Assert.assertEquals("Ada: 60.0 min es equivalente a 3600.0 s @" + USUARIO,
				escuchar("@ada converti 60 min a s"));
	}

	@Test
	public void capacidad() {
		Assert.assertEquals("Ada: 120.0 Pinta (pt) es equivalente a 15.0 Galón (gal) @" + USUARIO,
				escuchar("@ada pasa 120 pt a gal"));
	}

	
	public void capacidadDistinta() {
		// Assert.assertEquals("Ada: 120.0 Pinta (pt) es equivalente a 68.19 Litro @" +
		// USUARIO, escuchar("@ada pasa 120 pt a l"));
		Assert.assertEquals("Ada: No se encuentran las unidades Pinta (pt) y Litro (l) para la conversión. @" + USUARIO,
				escuchar("@ada pasa 120 pt a l"));
	}

	@Test
	public void masaDistinta() {
		Assert.assertEquals("Ada: 12.0 Gramo (gr) es equivalente a 0.42 Ozna (oz) @" + USUARIO,
				escuchar("@ada pasa 12 gr a oz"));
	}

	@Test
	public void longitud() {
		Assert.assertEquals("Ada: 12.0 Kilómetro (km) es equivalente a 12000.0 Metro (m) @" + USUARIO,
				escuchar("@ada convertir 12 km a m"));
	}

	
	public void longitudDistinta() {
		Assert.assertEquals("Ada: 254.0 Kilómetro (km) es equivalente a 232258.3 Yarda (yd) @" + USUARIO,
				escuchar("@ada convertir 254 km a yd"));
	}

	@Test
	public void cualquiera() {
		// Assert.assertEquals("Ada: No se puede convertir de km a mi @" + USUARIO,
		// escuchar("@ada convertir 254 km a mi"));
		Assert.assertEquals("Ada: Faltan unidades en la base de datos @" + USUARIO,
				escuchar("@ada convertir 254 km a mi"));
	}

	public void tiempos() {
		Assert.assertEquals("Ada: 3.5 h es equivalente a 12600.0 s @" + USUARIO, escuchar("@ada pasar de 3.5 h a s"));
	}

}
