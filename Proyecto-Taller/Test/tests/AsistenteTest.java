package tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.*;
import cs.Servidor;
import usuariosYAsistente.Usuario;

public class AsistenteTest {

	public final static String USUARIO = "delucas";
	static Usuario user;

	@BeforeClass
	public static void inicio() {
		new Thread() {
			public void run() {
			try {
				new Servidor(5050);
			} catch (Exception e) {
			}
			}
		}.start();
	}

	@Test
	public void agradecimiento() {

		user.("hola ada");
		user.recibirMensaje();

		String[] mensajes = { "�Muchas gracias, @ada!", "@ada gracias", "gracias @ada" };
		for (String mensaje : mensajes) {
			user.enviarMensaje(mensaje);
			String recibirMensaje = user.recibirMensaje();
			// System.out.println(recibirMensaje);
			Assert.assertEquals("Ada Lovelace: no es nada @" + USUARIO, recibirMensaje);
		}
	}

	@Test
	public void calculos() {
		user.enviarMensaje("@ada calcula 1+2");
		Assert.assertEquals("Ada Lovelace: la cuenta da: 3 @" + USUARIO, user.recibirMensaje());

		user.enviarMensaje("@ada cuanto es 5-2*2");
		Assert.assertEquals("Ada Lovelace: la cuenta da: 1 @" + USUARIO, user.recibirMensaje());

		user.enviarMensaje("@ada cuanto da 17+5^2");
		Assert.assertEquals("Ada Lovelace: la cuenta da: 42 @" + USUARIO, user.recibirMensaje());

	}

	@Test
	public void calcularConNegativos() {
		user.enviarMensaje("@ada resolve -1*(((1+1)^3*10)/80-6)*2");
		Assert.assertEquals("Ada Lovelace: la exprecion da: 10 @" + USUARIO, user.recibirMensaje());
	}

	@Test
	public void calculosCompuestos() {
		user.enviarMensaje("@ada resuelve (((1+1)^3*10)/80-6)*2-100-5+3");
		Assert.assertEquals("Ada Lovelace: la exprecion da: -112 @" + USUARIO, user.recibirMensaje());
	}

	@Test
	public void porcentaje() {
		user.enviarMensaje("@ada cuanto da 25%400");
		Assert.assertEquals("Ada Lovelace: la cuenta da: 100 @" + USUARIO, user.recibirMensaje());
	}

	@Test
	public void mixSupremo() {
		user.enviarMensaje("@ada cuanto es 10%((30+20)+((135-30)-5)^1)");
		Assert.assertEquals("Ada Lovelace: la exprecion da: 15 @" + USUARIO, user.recibirMensaje());
	}

	@Test
	public void fecha() {
		String texto = "Ada Lovelace: hoy es "
				+ new SimpleDateFormat("EEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy @").format(new Date()) + USUARIO;

		user.enviarMensaje("@ada cual es la fecha de hoy?");
		Assert.assertEquals(texto, user.recibirMensaje());
		user.enviarMensaje("@ada dame la fecha");
		Assert.assertEquals(texto, user.recibirMensaje());
	}

	@Test
	public void hora() {
		String[] mensajes = { "�qu� hora es, @ada?", "@ada, la hora por favor", "me dec�s la hora @ada?" };
		for (String mensaje : mensajes) {
			user.enviarMensaje(mensaje);
			Assert.assertEquals(
					"Ada Lovelace: son las " + new SimpleDateFormat("HH:mm").format(new Date()) + " @" + USUARIO,
					user.recibirMensaje());
		}
	}

	@Test
	public void dia() {
		String[] mensajes = { "@ada que dia es hoy?", "@ada dia hoy", "@ada decime el dia" };
		for (String mensaje : mensajes) {
			user.enviarMensaje(mensaje);
			// String esperado = "Ada Lovelace: hoy es " + new SimpleDateFormat("EEEEEEEEE").format(new Date());
			String esperado = "Ada Lovelace: hoy es "
					+ new SimpleDateFormat("EEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy").format(new Date()) + " @"
					+ USUARIO;
			Assert.assertEquals(esperado, user.recibirMensaje());
		}
	}
}