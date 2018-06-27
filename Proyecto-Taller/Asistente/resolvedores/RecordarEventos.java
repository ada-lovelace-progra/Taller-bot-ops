package resolvedores;

import java.util.Date;

import armadores.RespuestaGenerico;

/** 
 * Resolvedor, Agenda nuevos eventos en el futuro y permite ver los proximos eventos pendientes.
 */
public class RecordarEventos extends RespuestaGenerico {

	public RecordarEventos() {
	}

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
		Evento e = new Evento();
		e = e.proximoEvento();
		if(e!=null)
			return e.toString();
		else
			return "No hay próximos eventos";
	}

	@SuppressWarnings("deprecation")
	private String agregarEvento(String evento) {
		Evento e = new Evento();
		e.setFecha( new Date(Date.parse(evento)));
		e.setDescripcion("Evento del día " + evento);
		if(e.crearEvento(e))
			return "Evento agregado";
		else
			return "No se pudo agregar el evento";
	}

}
