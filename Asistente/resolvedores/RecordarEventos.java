package resolvedores;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import armadores.RespuestaGenerico;
import bdRespuestas.EventoBD;

/**
 * Resolvedor, Agenda nuevos eventos en el futuro y permite ver los proximos
 * eventos pendientes.
 */
public class RecordarEventos extends RespuestaGenerico {

	private static EventoBD e = new EventoBD();

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			mensaje = mensaje.substring(mensaje.indexOf(":") + 1);
			return agregarEvento(mensaje.substring(mensaje.indexOf(":") + 1));// solo le pasa la fecha del evento
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private String agregarEvento(String evento) {
		Matcher regex = Pattern.compile("([0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4})").matcher(evento);

		if (!regex.find())
			return "No se pudo agregar el evento";

		e.setFecha(new Date(Date.parse(regex.group(1))));
		e.setDescripcion("Evento del d�a " + evento.replace(regex.group(1), ""));
		if (e.crearEvento(e))
			return "Evento agregado";
		else
			return "No se pudo agregar el evento";
	}

	public String siguienteEvento() {
		EventoBD siguiente = e.proximoEvento();
		try {
			if (siguiente.getDescripcion() == null)
				Thread.sleep(10000);
			Thread.sleep(e.getFecha().getTime() - new Date().getTime());
		} catch (Exception e) {
			return null;
		}
		eliminarEvento(siguiente.getDescripcion());
		return siguiente.getDescripcion();
	}

	private void eliminarEvento(String descripcion) {
	}

}
