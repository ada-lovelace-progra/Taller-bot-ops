package resolvedores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import armadores.RespuestaGenerico;
import bdRespuestas.EventosBD;

/**
 * Resolvedor, Agenda nuevos eventos en el futuro y permite ver los proximos
 * eventos pendientes.
 */
public class RecordarEventos extends RespuestaGenerico {

	private static boolean salida = false;

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			if (mensaje.contains("agrega") || mensaje.contains("carga"))
				return agregarEvento(mensaje);
			else {
				EventosBD siguienteEvento = siguienteEvento(mensaje.substring(0, mensaje.indexOf(":")));
				if (siguienteEvento != null)
					return siguienteEvento.toString();
				else
					return "No hay eventos proximos";
			}
		}
		return null;
	}

	public String agregarEvento(String evento) {
		Matcher regexUsuario = Pattern.compile("^(\\w+):").matcher(evento);
		String usuario = "";
		if (regexUsuario.find())
			usuario = regexUsuario.group(1);
		
		evento = evento.substring(evento.lastIndexOf(": ") + 2);

		Matcher regexFecha = Pattern.compile("([0-9]{1,2})/([0-9]{1,2})/([0-9]{2,4})").matcher(evento);
		evento = evento.replaceFirst("[0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4}", "");

		Matcher regexHora = Pattern.compile("([0-9]{1,2}):([0-9]{1,2})").matcher(evento);
		if (!regexFecha.find())
			return "formato de fecha no valido";

		String hora = "00", minuto = "00";
		if (regexHora.find()) {
			hora = regexHora.group(1);
			minuto = regexHora.group(2);
		}

		evento = evento.replaceFirst("([0-9]{1,2}):([0-9]{1,2})", "").trim();

		String anio = regexFecha.group(3);
		anio = anio.length() < 3 ? "20" + anio : anio;
		EventosBD e = new EventosBD();
		e.setUsuario(usuario);
		e.setFecha(anio + "/" + regexFecha.group(2) + "/" + regexFecha.group(1) + "_" + hora + ":" + minuto);
		e.setDescripcion(evento);
		if (e.crearEvento(e)) {
			salida = true;
			return "Evento agregado";
		} else
			return "No se pudo agregar el evento";
	}

	public static EventosBD esperarSiguienteEvento(String usuario) {
		try {
			EventosBD siguiente = siguienteEvento(usuario);
			if (siguiente == null || siguiente.getDescripcion() == null)
				Thread.sleep(60000);
			else {
				long horaEvento = new SimpleDateFormat("yyyy/MM/dd_hh:mm").parse(siguiente.getFecha()).getTime();
				long horaActual = new Date().getTime();
				long espera = horaEvento - horaActual;
				while (espera > 1000) {
					Thread.sleep(1000);
					espera -= 1000;
					if (salida) {
						salida = false;
						return null;
					}
				}
				if (espera > 0)
					Thread.sleep(espera);
				eliminarEvento(siguiente.getFecha());
				return siguiente;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	private static EventosBD siguienteEvento(String usuario) {
		return new EventosBD().proximoEvento(usuario);
	}

	private static void eliminarEvento(String fecha) {
		new EventosBD().darDeBaja(fecha);
	}

	public static void main(String a[]) {
		RecordarEventos e = new RecordarEventos();
		String fecha = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(new Date().getTime() + 40000);
		e.agregarEvento("fede: bue " + fecha);
		// while (true)
		// System.out.println(e.siguienteEvento("fede"));
	}

}
