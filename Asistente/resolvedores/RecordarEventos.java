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

	private static Thread hilo;

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			return agregarEvento(mensaje);// solo le pasa la fecha del evento
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public String agregarEvento(String evento) {
		Matcher regexUsuario = Pattern.compile("(\\w+):").matcher(evento);
		String usuario = "";
		if (regexUsuario.find())
			usuario = regexUsuario.group(1);

		evento = evento.substring(evento.indexOf(":") + 1);

		Matcher regexFecha = Pattern.compile("([0-9]{1,2})/([0-9]{1,2})/([0-9]{2,4})").matcher(evento);
		evento = evento.replaceFirst("[0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4}", "");

		Matcher regexHora = Pattern.compile("([0-9]{1,2})").matcher(evento);
		if (!regexFecha.find())
			return "formato de fecha no valido";

		String hora, minuto, segundo;
		if (regexHora.find())
			hora = regexHora.group(1);
		else
			hora = "00";
		if (regexHora.find())
			minuto = regexHora.group(1);
		else
			minuto = "00";
		if (regexHora.find())
			segundo = regexHora.group(1);
		else
			segundo = "00";

		evento = evento.replaceAll("[0-9\\/:_]+", "").trim();

		String anio = regexFecha.group(3);
		anio = anio.length() < 3 ? "20" + anio : anio;
		EventosBD e = new EventosBD();
		e.setUsuario(usuario);
		e.setFecha(anio + "/" + regexFecha.group(2) + "/" + regexFecha.group(1) + "_" + hora + ":" + minuto + ":"
				+ segundo);
		e.setDescripcion(evento);
		if (e.crearEvento(e)) {
			if (hilo != null) {
				hilo.stop();
				hilo.start();
			}
			return "Evento agregado";
		} else
			return "No se pudo agregar el evento";

	}

	public Thread getThreadByName(String nombre) {
		for (Thread hilo : Thread.getAllStackTraces().keySet())
			if (hilo.getName().equals(nombre))
				return hilo;

		return null;
	}

	public static EventosBD siguienteEvento(String usuario) {
		hilo = Thread.currentThread();
		try {
			EventosBD e = new EventosBD();
			EventosBD siguiente = e.proximoEvento(usuario);
			if (siguiente == null || siguiente.getDescripcion() == null)
				Thread.sleep(10000);
			else {
				long horaEvento = new SimpleDateFormat("yyyy/MM/dd_hh:mm:ss").parse(siguiente.getFecha()).getTime();
				long horaActual = new Date().getTime();
				long espera = horaEvento - horaActual;
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

	private static void eliminarEvento(String fecha) {
		new EventosBD().darDeBaja(fecha);
	}

	public static void main(String a[]) {
		RecordarEventos e = new RecordarEventos();
		String fecha = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date().getTime() + 40000);
		e.agregarEvento("fede: bue " + fecha);
		// while (true)
		// System.out.println(e.siguienteEvento("fede"));
	}

}
