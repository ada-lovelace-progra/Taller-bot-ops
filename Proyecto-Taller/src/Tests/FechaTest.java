package Tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

import ada.Fecha;

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

	@Test
	public void hasta() {
		Assert.assertEquals("2 dias 2 semanas 20 años", Fecha.hasta(21, 4, 2038));
		Assert.assertEquals("4 dias 20 años", Fecha.hasta(9, 4, 2038));
		Assert.assertEquals("2 dias 2 semanas", Fecha.hasta(21, 4, 2018));
		Assert.assertEquals("4 dias 2 semanas 3 meses", Fecha.hasta(21, 7, 2018));
	}

	@Test
	public void desde() {
		Assert.assertEquals("4 dias", Fecha.desde(1, 4, 2018));
	}

	@Test
	public void fechaCompleta() {
		Assert.assertEquals(new SimpleDateFormat("EEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy").format(new Date()),
				Fecha.getFechaCompleta());
	}

}
