package pendientes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.*;

import resolvedores.RespuestaGenerico;

public class Fecha extends RespuestaGenerico {

	@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			if (mensaje.contains("ahora"))
				return now();

			else if (mensaje.contains("getfecha"))
				return getFecha();

			else if (mensaje.contains("semana"))
				return getFechaCompleta();

			else if (mensaje.contains("dia"))
				return getDiaDeLaSemana();

			else if (mensaje.contains("hora"))
				return getHora();

			else if (mensaje.contains("falta")) {
				Matcher asd = Pattern.compile(".*([0-9][0-9])/([0-9]+)/([0-9]+).*").matcher(mensaje);
				if (asd.find()) {
					int dia = Integer.parseInt(asd.group(1)), mes = Integer.parseInt(asd.group(2)),
							ano = Integer.parseInt(asd.group(3));
					return hasta(dia, mes, ano);
				}
			}

			else if (mensaje.contains("paso")) {
				Matcher asd = Pattern.compile(".*([0-9][0-9])/([0-9]+)/([0-9]+).*").matcher(mensaje);
				if (asd.find()) {
					int dia = Integer.parseInt(asd.group(1)), mes = Integer.parseInt(asd.group(2)),
							ano = Integer.parseInt(asd.group(3));
					return desde(dia, mes, ano);
				}
			}
		}
		return null;
	}

	// devuelve fecha actual en formato hora:min:seg dia/mes/a�o
	private String now() {
		return new SimpleDateFormat("hh:mm:ss - dd/MM/yyyy").format(new Date());
	}

	// devuelve hora:minutos
	private String getHora() {
		return new SimpleDateFormat("HH:mm").format(new Date());
	}

	// devuelve fecha actual en formato dia/mes/a�o
	public String getFecha() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	// devuelve la fecha actual en formato "viernes, 04 de mayo de 2018"
	private String getFechaCompleta() {
		return new SimpleDateFormat("EEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy").format(new Date());
	}

	// Devuelve dia de la semana actual
	private String getDiaDeLaSemana() {
		return new SimpleDateFormat("EEEEEEEEE").format(new Date());
	}

	// Diferencia entre dos fechas, devuelve cantidad de dias
	private String hasta(int dia, int mes, int ano) {

		DateTime startDate = new DateTime();
	    DateTime endDate = new DateTime( ano, mes, dia, 0, 0, 0, 0);
	    Days d = Days.daysBetween(startDate, endDate);
	    int days = d.getDays();
	    return "faltan " + days + " dias";
	    
//		Date actual = new Date();
//		Date FechaDada = new Date(new GregorianCalendar(ano, mes, dia).getTimeInMillis() );
//		Date hasta = new Date(FechaDada.getTime() - actual.getTime());
//		// return new SimpleDateFormat ("dd/MM/yyyy").format(hasta);
//		String[] aux = new SimpleDateFormat("dd/MM/yyyy").format(hasta).split("/");
//		int dias = Integer.parseInt(aux[0]);
//		int semanas = dias / 7;
//		dias %= 7;
//		int meses = Integer.parseInt(aux[1]) - 1;
//		int anos = Integer.parseInt(aux[2]) - 1970;
//		return "" + ((dias > 0 ? dias + " dia" : "") + (dias > 1 ? "s" : "")
//				+ (semanas > 0 ? " " + semanas + " semana" : "") + (semanas > 1 ? "s" : "")
//				+ (meses > 0 ? " " + meses + " mes" : "") + (meses > 1 ? "es" : "")
//				+ (anos > 0 ? " " + anos + " a�o" : "") + (anos > 1 ? "s" : "")).trim();
	}

	// Diferencia entre dos fechas, devuelve cantidad de dias
	private String desde(int dia, int mes, int ano) {
		DateTime startDate = new DateTime(ano, mes, dia, 0, 0, 0, 0);
	    DateTime endDate = new DateTime();
	    Days d = Days.daysBetween(startDate, endDate);
	    int days = d.getDays();
	    return "" + days + " dias";
	}

}
