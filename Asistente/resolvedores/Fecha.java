package resolvedores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.*;
import org.joda.time.format.*;

import armadores.RespuestaGenerico;

/**
 * Resolvedor, devuelve operaciones relacionadas a la fecha.
 * 
 * dias hasta dias desde hora semana fecha actual
 */
public class Fecha extends RespuestaGenerico {
	private Pattern patternFechaCompleta = Pattern.compile(".*([0-9][0-9])/([0-9]+)/([0-9]+).*");
	private Pattern patternDias = Pattern.compile(".* ([0-9]+) ([a-z]+).*");
	private DateTime fecha = null;
	private Date fecha2 = null;

	/**
	 * Se agrega un constructor para testear. recive s en formato dd/MM/aaaa y hace
	 * las cuentas en base a eso, asume que son las 10:30 de ese dia.
	 * 
	 * @param s
	 */
	public Fecha(String s) {
		Matcher regexFechaCompleta = patternFechaCompleta.matcher(s);
		if (regexFechaCompleta.find()) {
			int dia = Integer.parseInt(regexFechaCompleta.group(1)),
					mes = Integer.parseInt(regexFechaCompleta.group(2)),
					ano = Integer.parseInt(regexFechaCompleta.group(3));
			this.fecha = new DateTime(ano, mes, dia, 10, 30, 0, 0);
			this.fecha2 = new Date(ano - 1900, mes - 1, dia, 10, 30);
		}
	}

	@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			if (mensaje.contains("ahora"))
				return now();

			else if (mensaje.contains("getfecha"))
				return getFecha();

			else if (mensaje.contains("semana"))
				return getFechaCompleta();

			else if (mensaje.contains("hora"))
				return getHora();

			else if (mensaje.contains("falta")) {
				Matcher regexFechaCompleta = patternFechaCompleta.matcher(mensaje);
				if (regexFechaCompleta.find()) {
					int dia = Integer.parseInt(regexFechaCompleta.group(1)),
							mes = Integer.parseInt(regexFechaCompleta.group(2)),
							ano = Integer.parseInt(regexFechaCompleta.group(3));
					return hasta(dia, mes, ano);
				}
			} else if (mensaje.contains("paso")) {
				Matcher regexFechaCompleta = patternFechaCompleta.matcher(mensaje);
				if (regexFechaCompleta.find()) {
					int dia = Integer.parseInt(regexFechaCompleta.group(1)),
							mes = Integer.parseInt(regexFechaCompleta.group(2)),
							ano = Integer.parseInt(regexFechaCompleta.group(3));
					return desde(dia, mes, ano);
				}
			} else if (mensaje.contains("dentro")) {
				Matcher regexDias = patternDias.matcher(mensaje);
				if (regexDias.find()) {
					int dia = Integer.parseInt(regexDias.group(1));
					switch (regexDias.group(2)) {
					case "dias":
						return dentrodeDias(dia);
					case "meses":
						return dentrodeMeses(dia);
					}
				}
			} else if (mensaje.contains("hace")) {
				Matcher regexDias = patternDias.matcher(mensaje);
				if (regexDias.find()) {
					int dia = Integer.parseInt(regexDias.group(1));
					switch (regexDias.group(2)) {
					case "dias":
						return haceDias(dia);
					case "meses":
						return haceMeses(dia);
					}
				}
			} else if (mensaje.contains("dia"))
				return getDiaDeLaSemana();
		}
		return null;

	}

	// devuelve fecha actual en formato hora:min:seg dia/mes/a�o
	private String now() {
		return new SimpleDateFormat("hh:mm:ss - dd/MM/yyyy").format((this.fecha2 == null) ? new Date() : this.fecha2);
	}

	// devuelve hora:minutos
	private String getHora() {
		return new SimpleDateFormat("HH:mm").format((this.fecha2 == null) ? new Date() : this.fecha2);
	}

	// devuelve fecha actual en formato dia/mes/a�o
	public String getFecha() {
		return new SimpleDateFormat("dd/MM/yyyy").format((this.fecha2 == null) ? new Date() : this.fecha2);
	}

	// devuelve la fecha actual en formato "viernes, 04 de mayo de 2018"
	private String getFechaCompleta() {
		return new SimpleDateFormat("EEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy")
				.format((this.fecha2 == null) ? new Date() : this.fecha2);
	}

	///////////////////////////////////////
	// devuelve la fecha dentro de X dias
	private String dentrodeDias(int dia) {
		DateTime dateTime = (this.fecha == null) ? new DateTime() : this.fecha;
		dateTime = dateTime.plusDays(dia);
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");
		return "va a ser " + dtfOut.print(dateTime);
	}

	private String dentrodeMeses(int mes) {
		DateTime dateTime = (this.fecha == null) ? new DateTime() : this.fecha;
		dateTime = dateTime.plusMonths(mes);
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");
		return "va a ser " + dtfOut.print(dateTime);
	}

	private String haceDias(int dia) {
		DateTime dateTime = (this.fecha == null) ? new DateTime() : this.fecha;
		dateTime = dateTime.plusDays(dia * (-1));
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");
		return "" + dtfOut.print(dateTime);
	}

	private String haceMeses(int mes) {
		DateTime dateTime = (this.fecha == null) ? new DateTime() : this.fecha;
		dateTime = dateTime.plusMonths(mes * (-1));
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");
		return "" + dtfOut.print(dateTime);
	}
	///////////////////////////////////////// 7

	// Devuelve dia de la semana actual
	private String getDiaDeLaSemana() {
		return new SimpleDateFormat("EEEEEEEEE").format((this.fecha2 == null) ? new Date() : this.fecha2);
	}

	// Diferencia entre dos fechas, devuelve cantidad de dias
	private String hasta(int dia, int mes, int ano) {
		DateTime startDate = (this.fecha == null) ? new DateTime() : this.fecha;
		DateTime endDate = new DateTime(ano, mes, dia, 0, 0, 0, 0);
		Days d = Days.daysBetween(startDate, endDate);
		int days = d.getDays();
		return "faltan " + days + " dias";
	}

	// Diferencia entre dos fechas, devuelve cantidad de dias
	private String desde(int dia, int mes, int ano) {
		DateTime startDate = new DateTime(ano, mes, dia, 0, 0, 0, 0);
		DateTime endDate = (this.fecha == null) ? new DateTime() : this.fecha;
		Days d = Days.daysBetween(startDate, endDate);
		int days = d.getDays();
		return "" + days + " dias";
	}

}
