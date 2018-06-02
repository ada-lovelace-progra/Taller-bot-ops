package resolvedores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.*;
import org.joda.time.format.*;

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

			else if (mensaje.contains("hora"))
				return getHora();

			else if (mensaje.contains("falta")) {
				Matcher asd = Pattern.compile(".*([0-9][0-9])/([0-9]+)/([0-9]+).*").matcher(mensaje);
				if (asd.find()) {
					int dia = Integer.parseInt(asd.group(1)), mes = Integer.parseInt(asd.group(2)),
							ano = Integer.parseInt(asd.group(3));
					return hasta(dia, mes, ano);
				}
			} else if (mensaje.contains("paso")) {
				Matcher asd = Pattern.compile(".*([0-9][0-9])/([0-9]+)/([0-9]+).*").matcher(mensaje);
				if (asd.find()) {
					int dia = Integer.parseInt(asd.group(1)), mes = Integer.parseInt(asd.group(2)),
							ano = Integer.parseInt(asd.group(3));
					return desde(dia, mes, ano);
				}
			} else if (mensaje.contains("dentro")) {
				Matcher asd = Pattern.compile(".* ([0-9]+) ([a-z]+).*").matcher(mensaje);
				if (asd.find()) {
					int dia = Integer.parseInt(asd.group(1));
					switch (asd.group(2)) {
					case "dias":
						return dentrodeDias(dia);
					case "meses":
						return dentrodeMeses(dia);
					}
				}
			} else if (mensaje.contains("hace")) {
				Matcher asd = Pattern.compile(".* ([0-9]+) ([a-z]+).*").matcher(mensaje);
				if (asd.find()) {
					int dia = Integer.parseInt(asd.group(1));
					switch (asd.group(2)) {
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

	///////////////////////////////////////
	// devuelve la fecha dentro de X dias
	private String dentrodeDias(int dia) {
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(dia);
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");
		return "va a ser " + dtfOut.print(dateTime);
	}

	private String dentrodeMeses(int mes) {
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusMonths(mes);
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");
		return "va a ser " + dtfOut.print(dateTime);
	}

	private String haceDias(int dia) {
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(dia * (-1));
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");
		return "" + dtfOut.print(dateTime);
	}

	private String haceMeses(int mes) {
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusMonths(mes * (-1));
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");
		return "" + dtfOut.print(dateTime);
	}
	///////////////////////////////////////// 7

	// Devuelve dia de la semana actual
	private String getDiaDeLaSemana() {
		return new SimpleDateFormat("EEEEEEEEE").format(new Date());
	}

	// Diferencia entre dos fechas, devuelve cantidad de dias
	private String hasta(int dia, int mes, int ano) {
		DateTime startDate = new DateTime();
		DateTime endDate = new DateTime(ano, mes, dia, 0, 0, 0, 0);
		Days d = Days.daysBetween(startDate, endDate);
		int days = d.getDays();
		return "faltan " + days + " dias";
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
