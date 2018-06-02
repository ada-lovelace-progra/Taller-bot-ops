package tests;


import java.text.SimpleDateFormat;
import java.util.Date;


import org.junit.*;

import resolvedores.Unidades_S_Metrico;
import usuariosYAsistente.Asistente;


public class UnidadesTest {
	
	private static final String USUARIO = "ash";
	static Asistente ada;

	@BeforeClass
	public static void setup() {
		ada = new Asistente();
		ada.escuchar(USUARIO + ": " + "hola @ada");
	}

	private String formato(String mensaje) {
		return "Ada: " + mensaje + " @" + USUARIO;
	}

	private static String escuchar(String mensaje) {
		String escuchar = ada.escuchar(USUARIO + ": " + mensaje);
		if (escuchar.length() > 5)
			return escuchar.substring(4);
		return null;
	}	
	
	@Test
	public void mismoSistema() {
		Assert.assertEquals("Ada: 1000.0 gr es equivalente a 1.0 kg @" + USUARIO, escuchar("@ada pasar 1000 gr a kg"));
	}
	
	@Test
	public void distintosSistemas() {
		Assert.assertEquals("Ada: No se puede convertir de gr a pt @" + USUARIO, escuchar("@ada converti 1000 gr a pt"));
	}
	
	@Test
	public void tiempo() {
		Assert.assertEquals("Ada: 60.0 min es equivalente a 3600.0 s @" + USUARIO, escuchar("@ada converti 60 min a s"));
	}
	
	@Test
	public void capacidad() {
		Assert.assertEquals("Ada: 120.0 pt es equivalente a 15.0 gal @" + USUARIO, escuchar("@ada pasa 120 pt a gal"));
	}
	
	@Test
	public void capacidadDistinta() {
		Assert.assertEquals("Ada: 120.0 pt es equivalente a 68.19 l @" + USUARIO, escuchar("@ada pasa 120 pt a l"));
	}
	
	@Test
	public void masaDistinta() {
		Assert.assertEquals("Ada: 12.0 gr es equivalente a 0.42 oz @" + USUARIO, escuchar("@ada pasa 12 gr a oz"));
	}
	
	@Test
	public void longitud() {
		Assert.assertEquals("Ada: 12.0 km es equivalente a 12000.0 m @" + USUARIO, escuchar("@ada convertir 12 km a m"));
	}
	
	@Test
	public void longitudDistinta() {
		Assert.assertEquals("Ada: 254.0 km es equivalente a 277776.94 yd @" + USUARIO, escuchar("@ada convertir 254 km a yd"));
	}
	
	@Test
	public void cualquiera() {
		Assert.assertEquals("Ada: No se puede convertir de km a mi @" + USUARIO, escuchar("@ada convertir 254 km a mi"));
	}
	
	@Test
	public void tiempos() {
		Assert.assertEquals("Ada: 3.5 h es equivalente a 12600.0 s @" + USUARIO, escuchar("@ada pasar de 3.5 h a s"));
	}

}
