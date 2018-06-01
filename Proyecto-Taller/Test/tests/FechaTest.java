package tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pendientes.Fecha;
import usuariosYAsistente.Asistente;

public class FechaTest {

	public final static String USUARIO = "delucas";
	static Asistente ada;

	@BeforeClass
	public static void setup() {
		ada = new Asistente();
		ada.escuchar("hola @ada");
	}

	private static String escuchar(String mensaje) {
		String escuchar = ada.escuchar(USUARIO + ": " + mensaje);
		if (escuchar.length() > 4)
			return escuchar.substring(4);
		return null;
	}

	@Test
	public void hora() {
		Assert.assertEquals(new SimpleDateFormat("HH:mm").format(new Date()), escuchar("@ada hora"));
	}

	@Test
	public void dia() {
		Assert.assertEquals(new SimpleDateFormat("EEEEEEEEE").format(new Date()), escuchar(""));
	}

	@Test
	public void fecha() {
		Assert.assertEquals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), escuchar(""));
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
		Assert.assertEquals("1 semana 9 meses", escuchar(""));

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
		Assert.assertEquals(fecha, escuchar(""));
	}

}
