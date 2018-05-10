package tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import usuariosYAsistente.Asistente;
import usuariosYAsistente.Usuario;
import usuariosYAsistente.UsuarioGenerico;

class Asistente_SaludosTest {

	public final static String USUARIO = "delucas";
	Asistente ada = new Asistente();
	UsuarioGenerico user = new Usuario(USUARIO);

	@Test
	public void llamadas() {
		String[] mensajes = { "ada, necesito ayuda", "buenas ada", "dale ada, activa ameo" };
		ArrayList<String> respuestas = new ArrayList<String>();
		respuestas.add("Ada Lovelace: Hola @" + USUARIO);
		respuestas.add("Ada Lovelace: hola....@" + USUARIO);
		respuestas.add("Ada Lovelace: Buenos dias. En que puedo servirte @" + USUARIO);
		respuestas.add("Ada Lovelace: Buenos dias!! @" + USUARIO);
		respuestas.add("Ada Lovelace: en que puedo ayudarte @"+USUARIO);
		respuestas.add("Ada Lovelace: buenos dias @" + USUARIO);
		respuestas.add("Ada Lovelace: estoy para servirle @" + USUARIO);
		respuestas.add("Ada Lovelace: mas vale que valga la pena... estaba durmiendo... @" + USUARIO);
		respuestas.add("Ada Lovelace: mas vale que valga la pena... @" + USUARIO);

		for (String mensaje : mensajes) {
			user.enviarMensaje(mensaje);
			ada.ActivarAda(mensaje);
			String recibirMensaje = user.recibirMensaje();
			System.out.println(recibirMensaje);
			
			user.enviarMensaje("chau ada");
			System.out.println(user.recibirMensaje());
			
			Assert.assertTrue(respuestas.contains(recibirMensaje));
		}
	}

}
