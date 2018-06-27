package tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class FechaTest {

	public final static String USUARIO = "delucas";
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
	public void hasta() {
		//probar cuando facu cumple 23
		Assert.assertEquals("Ada: faltan 596 dias @delucas", escuchar("@ada cuanto falta para la fecha 14/02/2020"));
		
		//para el proximo mundial que juegue chile
		Assert.assertEquals("Ada: faltan 1447 dias @delucas", escuchar("@ada cuanto falta para la fecha 14/06/2022"));
	}

	@Test
	public void desde() {
		Assert.assertEquals("Ada: 7803 dias @delucas", escuchar("@ada cuanto paso desde la fecha 14/02/1997"));
	}

	@Test
	public void fechaCompleta() {
		String fecha = new SimpleDateFormat("EEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy").format(new Date());
		Assert.assertEquals(formato(fecha), escuchar("@ada dia de la semana es hoy"));
	}
	
	@Test
	public void dentrodeDias() {
		Assert.assertTrue(escuchar("@ada dia dentro de 2 dias").matches("Ada: va a ser [0-9]{2}/[0-9]{2}/2018 @delucas"));
	}
	
	@Test
	public void haceDias() {
		Assert.assertTrue(escuchar("@ada dia hace 5 dias").matches("Ada: [0-9]{2}/[0-9]{2}/2018 @delucas"));
	}

}
