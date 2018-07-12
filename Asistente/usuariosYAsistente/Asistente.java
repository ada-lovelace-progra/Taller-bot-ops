package usuariosYAsistente;

import resolvedores.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import armadores.Crear;
import armadores.RespuestaGenerico;

public class Asistente extends UsuarioGenerico {
	// el nombre lo hereda de usuario generico
	private String RespondoA;
	private RespuestaGenerico cadena;
	private RespuestaGenerico cadenaCompleta;
	private Llamada llamada;
	private RecordarEventos eventos = new RecordarEventos();

	public Asistente() {
		llamada = new Llamada();
		cadena = llamada;
		cadenaCompleta = Crear.Cadena();
		// el nombre se va a setear cuando lo llamen por primera vez
	}

	public String getEvento() {
		String siguienteEvento = "";
		while ((siguienteEvento = eventos.siguienteEvento()) != null)
			;
		return siguienteEvento;
	}

	public String escuchar(String entrada) {
		entrada = entrada.toLowerCase().trim();
		// nunca esta de mas un buen trim.... y el buen toLower para evitar preguntar
		// por mayuscula y minuscula
		RespondoA = " @" + entrada.substring(0, entrada.indexOf(":"));// aca guardo el nombre del usuario que me hablo

		RespondoA = RespondoA.substring(0, 1).toUpperCase() + RespondoA.substring(1);
		if (nombre == null)
			obtenerNombreAsistente(entrada);

		String retorno = null;
		if (nombre != null && entrada.contains("@" + nombre.toLowerCase())) {
			entrada = entrada.toLowerCase();
			String respuestaTemp = cadena.intentar(entrada);

			if (respuestaTemp != null) {// con esto verifico si pudo responder
				if (cadena.getClass() == Llamada.class) {
					// si respueta es de de la clase llamada entonces estaba inactivo
					cadena = cadenaCompleta;
					retorno = nombre + ": " + respuestaTemp + RespondoA;
				} else if (respuestaTemp.startsWith("-1-2")) {
					// si comienza con el codigo de salida seteo a respuesta solo en llamada
					respuestaTemp = respuestaTemp.substring(4);
					cadena = new Llamada();
					retorno = nombre + ": " + respuestaTemp + RespondoA;
					nombre = null;
				} else
					retorno = nombre + ": " + respuestaTemp + RespondoA;
			} else if (cadena.getClass() == Llamada.class)
				nombre = null;
		}
		return "----" + retorno;
	}

	private void obtenerNombreAsistente(String entrada) {
		Matcher regex = Pattern.compile(".*@([a-z]+) ?.*").matcher(entrada);
		if (regex.find())
			nombre = regex.group(1);
		else {
			regex = Pattern.compile(".*@(.*) ?.*").matcher(entrada);
			if (regex.find())
				nombre = regex.group(1);
		}

		if (nombre != null)
			nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
		RespuestaGenerico.nombre = nombre;
	}
}