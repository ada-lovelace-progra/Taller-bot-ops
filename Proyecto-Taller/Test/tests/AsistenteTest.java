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

		String[] mensajes = { "¡Muchas gracias, @ada!", "@ada gracias", "gracias @ada" };
		for (String mensaje : mensajes) {
			Assert.assertEquals("Ada: De nada @" + USUARIO, escuchar("gracias @ada"));
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
		Assert.assertEquals("Ada: la exprecion da: 15 @" + USUARIO, escuchar("@ada cuanto es 10%((30+20)+((135-30)-5)^1)"));
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

	@SuppressWarnings("deprecation")
	@Test
	public void hasta() {
		Date fecha = new Date();
		int DifAno = 0, DifMes = 0, DifSem = 0, DifDia = 1;
		Assert.assertEquals("1 dia", escuchar(""));

		DifAno = 0;
		DifMes = 6;
		DifSem = 2;
		DifDia = 0;
		Assert.assertEquals("3 dias 2 semanas 6 meses", escuchar(""));

		DifAno = 29;
		DifMes = 1;
		DifSem = 3;
		DifDia = 4;
		Assert.assertEquals("4 dias 3 semanas 1 mes 29 años", escuchar(""));

		DifAno = 0;
		DifMes = 9;
		DifSem = 0;
		DifDia = 4;
		Assert.assertEquals("1 semana 9 meses", escuchar(""));
		// Fecha.hasta(fecha.getDate() + DifDia + (7 * DifSem),fecha.getMonth() +
		// DifMes, fecha.getYear() + DifAno + 1900));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void desde() {
		Date fecha = new Date();
		int DifAno = 0, DifMes = 0, DifSem = 0, DifDia = 4;
		Assert.assertEquals("4 dias", escuchar(""));

		DifAno = 0;
		DifMes = 9;
		DifSem = 0;
		DifDia = 4;
		Assert.assertEquals(formato("1 semana 9 meses"), escuchar(""));

		DifAno = 0;
		DifMes = 6;
		DifSem = 2;
		DifDia = 0;
		Assert.assertEquals("3 dias 2 semanas 6 meses", escuchar(""));
		// Fecha.desde(fecha.getDate() + DifDia + (7 * DifSem),fecha.getMonth() +
		// DifMes, fecha.getYear() + DifAno + 1900));

		DifAno = 29;
		DifMes = 1;
		DifSem = 3;
		DifDia = 4;
		Assert.assertEquals("4 dias 3 semanas 1 mes 29 años", escuchar(""));
	}

	@Test
	public void fechaCompleta() {
		String fecha = new SimpleDateFormat("EEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy").format(new Date());
		Assert.assertEquals(formato(fecha), escuchar("@ada dia de la semana es hoy"));
	}

}