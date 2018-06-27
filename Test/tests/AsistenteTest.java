package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class AsistenteTest {

	private static final String USUARIO = "fede";
	static Asistente ada;

	@BeforeClass
	public static void setup() {
		ada = new Asistente();
		escuchar("hola @ada");
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
	public void agradecimiento() {

		String[] mensajes = { "¡Muchas gracias, @ada!", "@ada gracias", "gracias @ada" };
		for (String mensaje : mensajes) {
			Assert.assertEquals("Ada: De nada! @" + USUARIO, escuchar(mensaje));
		}
	}

	@Test
	public void asimov() {
		String resp = "1- Un robot no debe dañar a un ser humano o, por su inacción, dejar que un ser humano sufra daño.\r\n"
				+ "2- Un robot debe obedecer las órdenes que le son dadas por un ser humano, excepto si estas órdenes entran en conflicto con la Primera Ley.\r\n"
				+ "3- Un robot debe proteger su propia existencia, hasta donde esta protección no entre en conflicto con la Primera o la Segunda Ley.";
		String[] mensajes = { "@ada cuales son las leyes de la robotica ?", "que decia Isaac Asimov @ada" };
		for (String mensaje : mensajes) {
			Assert.assertEquals("Ada: " + resp + " @" + USUARIO, escuchar(mensaje));
		}
	}

	@Test
	public void chuck() {
		String[] mensajes = { "@ada tirame un dato de chuck norris", "que haria chuck @ada" };
		for (String mensaje : mensajes) {
			Assert.assertNotNull(escuchar(mensaje));
		}
	}

	@Test
	public void calculos() {
		Assert.assertEquals("Ada: La cuenta da: 3 @" + USUARIO, escuchar("@ada calcula 1+2"));

		Assert.assertEquals("Ada: La cuenta da: 1 @" + USUARIO, escuchar("@ada cuanto es 5-2*2"));

		Assert.assertEquals("Ada: La cuenta da: 42 @" + USUARIO, escuchar("@ada cuanto da 17+5^2"));

	}

	@Test
	public void calcularConNegativos() {
		Assert.assertEquals("Ada: La expresiÃ³n da: 10 @" + USUARIO, escuchar("@ada resolve -1*(((1+1)^3*10)/80-6)*2"));
	}

	@Test
	public void calculosCompuestos() {
		Assert.assertEquals("Ada: La expresiÃ³n da: -112 @" + USUARIO,
				escuchar("@ada resuelve (((1+1)^3*10)/80-6)*2-100-5+3"));
	}

	@Test
	public void porcentaje() {
		Assert.assertEquals("Ada: La cuenta da: 100 @" + USUARIO, escuchar("@ada cuanto da 25%400"));
	}

	@Test
	public void mixSupremo() {
		Assert.assertEquals("Ada: La expresiÃ³n da: 15 @" + USUARIO,
				escuchar("@ada cuanto es 10%((30+20)+((135-30)-5)^1)"));
	}

	@Test
	public void hora() {
		String hora = new SimpleDateFormat("HH:mm").format(new Date());
		Assert.assertEquals(formato(hora), escuchar("@ada la hora"));
	}

	@Test
	public void dia() {
		String dia = new SimpleDateFormat("EEEEEEEEE").format(new Date());
		Assert.assertEquals(formato(dia), escuchar("@ada dia"));
	}

	@Test
	public void fecha() {
		String dia = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		Assert.assertEquals(formato(dia), escuchar("@ada getfecha"));
	}

	@Test
	public void fechaCompleta() {
		String fecha = new SimpleDateFormat("EEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy").format(new Date());
		Assert.assertEquals(formato(fecha), escuchar("@ada dia de la semana es hoy"));
	}

	@Test
	public void giphy() {
		String[] mensajes = { "@ada gif de perros", "@ada gif de algo", "@ada giphy de fiesta" };
		for (String mensaje : mensajes) {
			Assert.assertTrue(escuchar(mensaje).contains("<img"));
		}
	}

	@Test
	public void gag9() {
		String[] mensajes = { "@ada 9gag", "@ada imagen de 9gag" };
		for (String mensaje : mensajes) {
			Assert.assertTrue(escuchar(mensaje).contains("<img"));
		}
	}

	@Test
	public void clima() {
		String[] mensajes = { "@ada clima", "@ada me decis el clima", "@ada como esta el clima" };
		for (String mensaje : mensajes) {
			String escuchar = escuchar(mensaje);
			Assert.assertTrue(escuchar.contains("openweathermap"));
		}
	}

	@Test
	public void wikipedia() {
		String respuesta = "Ada: Segun Wikipedia La programación es un proceso que se utiliza para idear y ordenar las <b>acciones</b> que se realizarán en el marco de un proyecto;al anuncio de las partes que componen un acto o espectáculo; a la preparación de máquinas para que cumplan con una cierta tarea en un momento determinado; a la elaboración de programas para la resolución de problemas mediante ordenadores; y a la preparación de los datos necesarios para obtener una solución de un problema. @"
				+ USUARIO;
		String[] mensajes = { "@ada me buscas informacion sobre programacion" };
		for (String mensaje : mensajes) {
			Assert.assertEquals(respuesta, escuchar(mensaje));
		}
	}

	@Test
	public void pensando() {
		int dijo = 0;
		escuchar("@ada jueguito");
		String resp = escuchar("@ada jueguito bueno dale");
		Pattern regex = Pattern.compile(".* ([0-9]+).*");
		Matcher match = regex.matcher(resp);
		if (match.find())
			dijo = Integer.parseInt(match.group(1));
		int pensado = 56;
		while (dijo != pensado) {
			if (dijo > pensado)
				resp = escuchar("@ada mas chico");
			else if (dijo < pensado)
				resp = escuchar("@ada mas grande");
			else
				assertTrue(true);
			match = regex.matcher(resp);
			if (match.find())
				dijo = Integer.parseInt(match.group(1));
			else
				assertTrue(false);
			System.out.println(dijo);
		}
		if (dijo == pensado)
			if (escuchar("@ada jueguito ganaste!").contains("GANE"))
				assertTrue(true);
	}

	@Test(timeout = 250)
	public void adivinando() {
		int dije = 66;
		int max = 9999;
		int min = 0;
		String resp;
		boolean corriendo = true;
		while (corriendo) {
			dije = (int) (Math.random() * (max - min)) + min;
			resp = escuchar("@ada es " + dije + "?");
			System.out.println(dije);
			if (resp.contains("grande"))
				min = dije + 1;
			else if (resp.contains("chico"))
				max = dije - 1;
			else if (resp.contains("ganaste")) {
				assertTrue(true);
				corriendo = false;
			}
		}
	}

}