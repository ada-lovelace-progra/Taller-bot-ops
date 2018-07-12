package tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import resolvedores.Fecha;

public class FechaTest {

	public final static String USUARIO = "delucas";
	static Fecha f;

	@BeforeClass
	public static void setup() {
		f = new Fecha( "15/07/2018" );
	}

	@Test
	public void hora() {
		Assert.assertEquals("10:30", f.intentarResponder("hora"));		
	}

	@Test
	public void dia() {
		Assert.assertEquals("domingo", f.intentarResponder("dia"));	
	}

	@Test
	public void fecha() {
		Assert.assertEquals("15/07/2018", f.intentarResponder("fecha getfecha"));		
	}
	
	@Test
	public void ahora() {
		Assert.assertEquals("10:30:00 - 15/07/2018", f.intentarResponder("ahora"));		
	}
	
	
	@Test
	public void hasta() {
		Assert.assertEquals("faltan 9 dias", f.intentarResponder("cuanto falta para el 25/07/2018"));	
	}

	@Test
	public void desde() {
		Assert.assertEquals("10 dias", f.intentarResponder("cuanto paso de 05/07/2018"));	
	}

	@Test
	public void fechaCompleta() {
		Assert.assertEquals("domingo, 15 de julio de 2018", f.intentarResponder("fecha completa semana"));
	}

	
	@Test
	public void dentrodeDias() {
		Assert.assertEquals("va a ser 20/07/2018", f.intentarResponder("fecha dentro de 5 dias"));
	}
	
	@Test
	public void haceDias() {
		Assert.assertEquals("10/07/2018", f.intentarResponder("fecha hace 5 dias"));
	}

}
