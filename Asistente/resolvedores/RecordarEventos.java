package resolvedores;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import armadores.RespuestaGenerico;
import bdResponderGenerico.EventoBD;

/**
 * Resolvedor, Agenda nuevos eventos en el futuro y permite ver los proximos
 * eventos pendientes.
 */
public class RecordarEventos extends RespuestaGenerico {

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			if ((mensaje.matches(".*" + "agrega" + ".*"))) // identifico que el pedido es agregar un evento
			{
				mensaje = mensaje.substring(mensaje.indexOf(":") + 1);
				return agregarEvento(mensaje.substring(mensaje.indexOf(":") + 1));// solo le pasa la fecha del evento

			} else if (mensaje.matches(".*" + "proximo" + ".*")) {
				return proximoEvento();
			}
		}
		return null;
	}

	private String proximoEvento() {
		EventoBD e = new EventoBD();
		e = e.proximoEvento();
		if (e != null)
			return e.toString();
		else
			return "No hay eventos para el dia de la fecha";
	}

	@SuppressWarnings("deprecation")
	private String agregarEvento(String evento) {
		EventoBD e = new EventoBD();
		Matcher regex = Pattern.compile("([0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4})").matcher(evento);

		if (!regex.find())
			return "No se pudo agregar el evento";

		e.setFecha(new Date(Date.parse(regex.group(1))));
		e.setDescripcion("Evento del día " + evento.replace(regex.group(1), ""));
		if (e.crearEvento(e))
			return "Evento agregado";
		else
			return "No se pudo agregar el evento";
	}

}
