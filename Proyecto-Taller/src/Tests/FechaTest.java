package Tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import ada.Fecha;

public class FechaTest {

	@Test
	public void hora() {
		Assert.assertEquals( "17:44", Fecha.getHora() );
	}
	
	@Test
	public void dia() {
		Assert.assertEquals( "Viernes", Fecha.getDiaDeLaSemana() );
	}
	
	@Test
	public void fecha() {
		Assert.assertEquals( "04/05/2018", Fecha.getFecha() );
	}
	
	@Test
	public void hasta() {
		Assert.assertEquals( "5 dias", Fecha.hasta( 10, 4, 2018) );
	}
	
	
}
