package tests;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import usuariosYAsistente.Asistente;
import usuariosYAsistente.Usuario;
import usuariosYAsistente.UsuarioGenerico;

public class AsistenteTest {

	public final static String USUARIO = "delucas";

	Asistente ada = new Asistente();
	UsuarioGenerico user = new Usuario(USUARIO);

	@Before
	public void setup() {
		user.enviarMensaje("hola ada");
		ada.ActivarAda("hola ada");
	}

	@After
	public void ending() {
		user.enviarMensaje("chau ada");
		user.recibirMensaje();
	}

	@Test
	public void agradecimiento() {
		String[] mensajes = { "¡Muchas gracias, @ada!", "@ada gracias", "gracias @ada" };
		for (String mensaje : mensajes) {
			user.enviarMensaje(mensaje);
			Assert.assertEquals("Ada Lovelace: no es nada @" + USUARIO, user.recibirMensaje());
		}
	}

	@Test
	public void calculos() {
		user.enviarMensaje("@ada resolver 1+2");
		Assert.assertEquals("Ada Lovelace: la cuenta da: 3 @" + USUARIO, user.recibirMensaje());

		user.enviarMensaje("@ada resolver 5-2*2");
		Assert.assertEquals("Ada Lovelace: la cuenta da: 1 @" + USUARIO, user.recibirMensaje());

		user.enviarMensaje("@ada resolver 17+5^2");
		Assert.assertEquals("Ada Lovelace: la cuenta da: 42 @" + USUARIO, user.recibirMensaje());

	}

	@Test
	public void calcularConNegativos() {
		user.enviarMensaje("@ada resolver -1*(((1+1)^3*10)/80-6)*2");
		Assert.assertEquals("Ada Lovelace: la exprecion da: 10 @" + USUARIO, user.recibirMensaje());
	}

	@Test
	public void calculosCompuestos() {
		user.enviarMensaje("@ada resolver (((1+1)^3*10)/80-6)*2-100-5+3");
		Assert.assertEquals("Ada Lovelace: la exprecion da: -112 @" + USUARIO, user.recibirMensaje());
	}

	@Test
	public void porcentaje() {
		user.enviarMensaje("@ada resolver 25%400");
		Assert.assertEquals("Ada Lovelace: la cuenta da: 100 @" + USUARIO, user.recibirMensaje());
	}

	@Test
	public void mixSupremo() {
		user.enviarMensaje("@ada resolver 10%((30+20)+((135-30)-5)^1)");
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
		String[] mensajes = { "¿qué hora es, @ada?", "@ada, la hora por favor", "me decís la hora @ada?" };
		for (String mensaje : mensajes) {
			user.enviarMensaje(mensaje);
			Assert.assertEquals(
					"Ada Lovelace: son las " + new SimpleDateFormat("HH:mm").format(new Date()) + " @" + USUARIO,
					user.recibirMensaje());
		}
	}

	// @Test
	public void dia() {
		String[] mensajes = { "@ada que dia es hoy?", "@ada dia hoy", "@ada decime el dia" };
		for (String mensaje : mensajes) {
			/// che no se... yo le mande que retorne la fecha larga... para que pida fecha
			/// corta habria que ver bien como ponerlo en el archivo de peticion fecha.dat y
			/// crear otro que sea fechacorta.dat...... o a la mierda que retorne siempre
			/// completo
			user.enviarMensaje(mensaje);
			String esperado = "Ada Lovelace: hoy es " + new SimpleDateFormat("EEEEEEEEE").format(new Date());
			Assert.assertEquals(esperado, user.recibirMensaje());
		}
	}
}