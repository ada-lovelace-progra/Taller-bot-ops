package usuariosYAsistente;

import resolvedores.Despedida;
import resolvedores.Llamada;
import resolvedores.RespuestaGenerico;

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

	private RespuestaGenerico seteoCadenaDeRespuestas() {
		// aca deberia armar la cadena y retornar el primer eslabon...
		// pero como que faltan todos los eslabones....
		Despedida despedida = new Despedida();
		// despedida.siguiente(new Default()); esta linea no hace falta en el ultimo
		// eslabon porque se setea directamente en el constructor
		return despedida;
	}

	public String escuchar(String entrada) {
		entrada = entrada.toLowerCase().trim(); // nunca esta de mas un buen trim.... y el buen toLower para evitar
												// preguntar por mayuscula y minuscula
		RespondoA = " @" + entrada.substring(0, entrada.indexOf(":"));// aca guardo el nombre del usuario que me hablo
		if (nombre == null)
			obtenerNombreAsistente(entrada);
		if (nombre != null)
			entrada = entrada.toLowerCase().replace("@" + nombre, "");
		// realmente creo que no hace falta el replace()... onda si usamos expreciones
		// regulares le va a chupar un huevo si dice o no el @nombre pero no esta de mas

		String respuestaTemp = respuesta.intentarResponder(entrada);
		if (respuestaTemp != null) {// con esto verifico si pudo responder
			if (respuesta.getClass() == Llamada.class)
				// si respueta es de de la clase llamada entonces estaba inactivo
				respuesta = seteoCadenaDeRespuestas();
			else if (/* respuesta.getClass() == Despedida.class && */ respuestaTemp.startsWith("-1-2")) {
				// si es de la clase despedida y comienza con el codigo de salida seteo a
				// respuesta para que solo tenga un eslabon y sea el de llamada
				respuestaTemp = respuestaTemp.substring(4);
				respuestaTemp = normalizarCadena(respuestaTemp);
				respuesta = new Llamada();
			}
			return nombre + ": " + respuestaTemp + RespondoA; // aca lo formateo lindo
		}
		return null;
	}

	private void obtenerNombreAsistente(String entrada) {
		/////////////////////////////////////////////////////////////////////////////
		// nada.... es una garcha aveces regex....
		Matcher asd = Pattern.compile(".*@(\\S+).*").matcher(entrada);
		if (asd.find())
			nombre = asd.group(1);
		else {
			asd = Pattern.compile(".*@(.*).*").matcher(entrada);
			if (asd.find())
				nombre = asd.group(1);
		}
		/////////////////////////////////////////////////////////////////////////////

		if (nombre != null)
			nombre = "@" + normalizarCadena(nombre);
		// lo formateo piola para que quede Ada o Jenkins... por si lo llaman ADA o ada
	}

	private String normalizarCadena(String cad) {
		String retorno = "";
		for (String temp : cad.split(".")) {
			temp = temp.trim();
			retorno += temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase() + ". ";
		}
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