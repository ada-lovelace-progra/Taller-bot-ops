package tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

import pendientes.Fecha;

public class FechaTest {

	@Test
	public void hora() {
		Assert.assertEquals(new SimpleDateFormat("HH:mm").format(new Date()), Fecha.getHora());
	}

	@Test
	public void dia() {
		Assert.assertEquals(new SimpleDateFormat("EEEEEEEEE").format(new Date()), Fecha.getDiaDeLaSemana());
	}

	@Test
	public void fecha() {
		Assert.assertEquals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), Fecha.getFecha());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void hasta() {
		Date fecha = new Date();
		int DifAno = 0, DifMes = 0, DifSem = 0, DifDia = 1;
		Assert.assertEquals("1 dia", Fecha.hasta(fecha.getDate() + DifDia + (7 * DifSem), fecha.getMonth() + DifMes,
				fecha.getYear() + DifAno + 1900));

		DifAno = 0;
		DifMes = 6;
		DifSem = 2;
		DifDia = 0;
		Assert.assertEquals("3 dias 2 semanas 6 meses", Fecha.hasta(fecha.getDate() + DifDia + (7 * DifSem),
				fecha.getMonth() + DifMes, fecha.getYear() + DifAno + 1900));

		DifAno = 29;
		DifMes = 1;
		DifSem = 3;
		DifDia = 4;
		Assert.assertEquals("4 dias 3 semanas 1 mes 29 años", Fecha.hasta(fecha.getDate() + DifDia + (7 * DifSem),
				fecha.getMonth() + DifMes, fecha.getYear() + DifAno + 1900));

		DifAno = 0;
		DifMes = 9;
		DifSem = 0;
		DifDia = 4;
		Assert.assertEquals("1 semana 9 meses", Fecha.hasta(fecha.getDate() + DifDia + (7 * DifSem),
				fecha.getMonth() + DifMes, fecha.getYear() + DifAno + 1900));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void desde() {
		Date fecha = new Date();
		int DifAno = 0, DifMes = 0, DifSem = 0, DifDia = 4;
		Assert.assertEquals("4 dias", Fecha.desde(fecha.getDate() + DifDia + (7 * DifSem), fecha.getMonth() + DifMes,
				fecha.getYear() + DifAno + 1900));

		DifAno = 0;
		DifMes = 9;
		DifSem = 0;
		DifDia = 4;
		Assert.assertEquals("1 semana 9 meses", Fecha.desde(fecha.getDate() + DifDia + (7 * DifSem),
				fecha.getMonth() + DifMes, fecha.getYear() + DifAno + 1900));

		DifAno = 0;
		DifMes = 6;
		DifSem = 2;
		DifDia = 0;
		Assert.assertEquals("3 dias 2 semanas 6 meses", Fecha.desde(fecha.getDate() + DifDia + (7 * DifSem),
				fecha.getMonth() + DifMes, fecha.getYear() + DifAno + 1900));

		DifAno = 29;
		DifMes = 1;
		DifSem = 3;
		DifDia = 4;
		Assert.assertEquals("4 dias 3 semanas 1 mes 29 años", Fecha.desde(fecha.getDate() + DifDia + (7 * DifSem),
				fecha.getMonth() + DifMes, fecha.getYear() + DifAno + 1900));
	}

	@Test
	public void fechaCompleta() {
		Assert.assertEquals(new SimpleDateFormat("EEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy").format(new Date()),
				Fecha.getFechaCompleta());
	}

}
