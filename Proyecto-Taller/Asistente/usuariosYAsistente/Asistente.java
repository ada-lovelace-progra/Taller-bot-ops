package usuariosYAsistente;

import resolvedores.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Asistente extends UsuarioGenerico {
	// el nombre lo hereda de usuario generico
	private String RespondoA;
	private RespuestaGenerico respuesta;

	public Asistente() {
		respuesta = new Llamada();
		// el nombre se va a setear cuando lo llamen por primera vez
	}

	public String escuchar(String entrada) {
		entrada = entrada.toLowerCase().trim(); // nunca esta de mas un buen trim.... y el buen toLower para evitar
												// preguntar por mayuscula y minuscula
		RespondoA = " @" + entrada.substring(0, entrada.indexOf(":"));// aca guardo el nombre del usuario que me hablo
		if (nombre == null)
			obtenerNombreAsistente(entrada);

		String retorno = null;
		if (nombre != null && entrada.contains("@" + nombre.toLowerCase())) {
			entrada = entrada.toLowerCase();
			String respuestaTemp = respuesta.intentar(entrada);

			if (respuestaTemp != null) {// con esto verifico si pudo responder
				if (respuesta.getClass() == Llamada.class) {
					// si respueta es de de la clase llamada entonces estaba inactivo
					respuesta = Crear.Cadena();
					retorno = nombre + ": " + normalizarCadena(respuestaTemp) + RespondoA;
				} else if (respuestaTemp.startsWith("-1-2")) {
					// si comienza con el codigo de salida seteo a
					// respuesta para que solo tenga un eslabon y sea el de llamada
					respuestaTemp = respuestaTemp.substring(4);
					respuesta = new Llamada();
					retorno = nombre + ": " + normalizarCadena(respuestaTemp) + RespondoA;
					nombre = null;
				} else
					retorno = nombre + ": " + normalizarCadena(respuestaTemp) + RespondoA;
			} else if (respuesta.getClass() == Llamada.class)
				nombre = null;
		}
		return retorno;
	}

	private void obtenerNombreAsistente(String entrada) {
		/////////////////////////////////////////////////////////////////////////////
		// nada.... es una garcha aveces regex....
		Matcher asd = Pattern.compile(".*@(\\S+) ?.*").matcher(entrada);
		if (asd.find())
			nombre = asd.group(1);
		else {
			asd = Pattern.compile(".*@(.*) ?.*").matcher(entrada);
			if (asd.find())
				nombre = asd.group(1);
		}
		/////////////////////////////////////////////////////////////////////////////

		if (nombre != null)
			nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
		// lo formateo piola para que quede Ada o Jenkins... por si lo llaman ADA o ada
		RespuestaGenerico.nombre = nombre;
	}

	private String normalizarCadena(String cad) {
		String retorno = "";
		for (String temp : cad.split(".")) {
			temp = temp.trim();
			retorno += temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase() + ". ";
		}
		if (retorno == "")
			retorno = cad.substring(0, 1).toUpperCase() + cad.substring(1).toLowerCase();
		return retorno;
	}

	/*
	 * DEJO ESTO DE GUIA PARA HACER LAS CLASES... case "simpsons":
	 * cargarLista("respuestas_simpsons"); return (tabla.get("respuestas_"
	 * +clave).get(tabla.get(clave).indexOf(cad)));
	 * 
	 * case "despedidas": activo = false; tabla.clear(); cargarLista("llamadas");
	 * return (respuesta(clave));
	 * 
	 * case "cuenta": String aux = cad.substring(cad.lastIndexOf(" ")); return
	 * ("la " + (aux.length() < 12 ? "cuenta" : "exprecion") + " da: " + new
	 * CalculoString().calcularFormat(aux,"%.3f"));
	 * 
	 * case "fecha": return ("hoy es " + Fecha.getFechaCompleta() + RespondoA);
	 * 
	 * case "hora": return ("son las " + Fecha.getHora() + RespondoA);
	 * 
	 * case "todo_bien": String[] lista = { "todobien" };
	 * cargarListaEsperadas(lista);
	 * 
	 * if (cad.matches(".*gracias.*")) { return ("no es nada" + RespondoA);
	 * 
	 * if (entrada.contains("@ada")) { return (respuesta("nose"));
	 * 
	 * if (consulta("nombrada", cad)) { return (respuesta("nombrada") + RespondoA);
	 * 
	 */
}