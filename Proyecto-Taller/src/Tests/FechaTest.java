package Tests;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import ada.Fecha;

public class FechaTest {

	@Test
	public void hora() {
		Assert.assertEquals( "10:44" , Fecha.getHora() );
	}
	
	@Test
	public void dia() {
		Assert.assertEquals( "Sabado", Fecha.getDiaDeLaSemana() );
	}
	
	@Test
	public void fecha() {
		Assert.assertEquals( "05/05/2018", Fecha.getFecha() );
	}
	
	@Test
	public void hasta() {
		Assert.assertEquals( "4 dias", Fecha.hasta( 10, 4, 2018) );
	}
	
	@Test
	public void desde() {
		Assert.assertEquals( "4 dias", Fecha.desde( 1, 4, 2018) );
	}
	
	
	@Test
	public void fechaCompleta() {
		Assert.assertEquals( "s�bado, 05 de mayo de 2018", Fecha.getFechaCompleta() );
	}
	
}
