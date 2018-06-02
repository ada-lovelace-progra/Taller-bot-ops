package tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.*;
import cs.Servidor;
import usuariosYAsistente.Asistente;
import usuariosYAsistente.Usuario;

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

		String[] mensajes = { "�Muchas gracias, @ada!", "@ada gracias", "gracias @ada" };
		for (String mensaje : mensajes) {
			Assert.assertEquals("Ada: De nada! @" + USUARIO, escuchar(mensaje));
		}
	}

	@Test
	public void asimov() {
		String resp = "1- Un robot no debe da�ar a un ser humano o, por su inacci�n, dejar que un ser humano sufra da�o.\r\n"
				+ "2- Un robot debe obedecer las �rdenes que le son dadas por un ser humano, excepto si estas �rdenes entran en conflicto con la Primera Ley.\r\n"
				+ "3- Un robot debe proteger su propia existencia, hasta donde esta protecci�n no entre en conflicto con la Primera o la Segunda Ley.";
		String[] mensajes = { "@ada cuales son las leyes de la robotica ?", "que decia Isaac Asimov @ada" };
		for (String mensaje : mensajes) {
			Assert.assertEquals("Ada: " + resp + " @" + USUARIO, escuchar(mensaje));
		}
	}

	@Test
	public void calculos() {
		Assert.assertEquals("Ada: la cuenta da: 3 @" + USUARIO, escuchar("@ada calcula 1+2"));

		Assert.assertEquals("Ada: la cuenta da: 1 @" + USUARIO, escuchar("@ada cuanto es 5-2*2"));

		Assert.assertEquals("Ada: la cuenta da: 42 @" + USUARIO, escuchar("@ada cuanto da 17+5^2"));

	}

	@Test
	public void calcularConNegativos() {
		Assert.assertEquals("Ada: la exprecion da: 10 @" + USUARIO, escuchar("@ada resolve -1*(((1+1)^3*10)/80-6)*2"));
	}

	@Test
	public void calculosCompuestos() {
		Assert.assertEquals("Ada: la exprecion da: -112 @" + USUARIO,
				escuchar("@ada resuelve (((1+1)^3*10)/80-6)*2-100-5+3"));
	}

	@Test
	public void porcentaje() {
		Assert.assertEquals("Ada: la cuenta da: 100 @" + USUARIO, escuchar("@ada cuanto da 25%400"));
	}

	@Test
	public void mixSupremo() {
		Assert.assertEquals("Ada: la exprecion da: 15 @" + USUARIO,
				escuchar("@ada cuanto es 10%((30+20)+((135-30)-5)^1)"));
	}

	@Test
	public void hora() {
		String hora = new SimpleDateFormat("HH:mm").format(new Date());
		Assert.assertEquals(formato(hora), escuchar("@ada hora"));
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

}