package Tests;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

import ada.Fecha;

public class FechaTest {

	@Test
	public void hora() {
		Assert.assertEquals(new SimpleDateFormat("HH:mm").format(new Date())  , Fecha.getHora() );
	}
	
	@Test
	public void dia() {
		Assert.assertEquals( new SimpleDateFormat("EEEEEEEEE").format(new Date()), Fecha.getDiaDeLaSemana() );
	}
	
	@Test
	public void fecha() {
		Assert.assertEquals( "05/05/2018", Fecha.getFecha() );
	}
	
	@Test
	public void hasta() {
		Assert.assertEquals( "4 dias", Fecha.hasta( 10, 4, 2038) );
	}
	
	@Test
	public void desde() {
		Assert.assertEquals( "4 dias", Fecha.desde( 1, 4, 2018) );
	}
	
	
	@Test
	public void fechaCompleta() {
		Assert.assertEquals(  new SimpleDateFormat("EEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy").format(new Date()), Fecha.getFechaCompleta() );
	}
	
}
