package tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class BuscarInformacionWikiGoogleTest {

	public final static String USUARIO = "delucas";

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
	public void wikipedia() {
		String respuesta = "Ada: Segun Wikipedia La programación es un proceso que se utiliza para idear y ordenar las <b>acciones</b> que se realizarán en el marco de un proyecto;al anuncio de las partes que componen un acto o espectáculo; a la preparación de máquinas para que cumplan con una cierta tarea en un momento determinado; a la elaboración de programas para la resolución de problemas mediante ordenadores; y a la preparación de los datos necesarios para obtener una solución de un problema. @delucas";
		String[] mensajes = { "@ada me buscas informacion sobre programacion" };
		for (String mensaje : mensajes) {
			Assert.assertEquals(respuesta, escucha(mensaje));
		}
	}

}
