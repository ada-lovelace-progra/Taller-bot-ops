package pendientes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import resolvedores.RespuestaGenerico;

public class Fecha extends RespuestaGenerico {

	@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			if (mensaje.contains("ahora"))
				return now();

			if (mensaje.contains("getfecha"))
				return getFecha();

			if (mensaje.contains("semana"))
				return getFechaCompleta();

			if (mensaje.contains("dia"))
				return getDiaDeLaSemana();

			if (mensaje.contains("hora"))
				return getHora();

			if (mensaje.contains("falta"))
				return hasta(1, 1, 3001);

			if (mensaje.contains("paso"))
				return desde(3, 3, 2000);
		}
		return null;
	}

	// devuelve fecha actual en formato hora:min:seg dia/mes/año
	private String now() {
		return new SimpleDateFormat("hh:mm:ss - dd/MM/yyyy").format(new Date());
	}

	// devuelve hora:minutos
	private String getHora() {
		return new SimpleDateFormat("HH:mm").format(new Date());
	}

	// devuelve fecha actual en formato dia/mes/año
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
		Date actual = new Date();

		Date FechaDada = new Date(new GregorianCalendar(ano, mes, dia).getTimeInMillis());
		Date hasta = new Date(FechaDada.getTime() - actual.getTime());
		// return new SimpleDateFormat ("hh:mm:ss - dd/MM/yyyy").format(hasta);
		String[] aux = new SimpleDateFormat("dd/MM/yyyy").format(hasta).split("/");
		int dias = Integer.parseInt(aux[0]);
		int semanas = dias / 7;
		dias %= 7;
		int meses = Integer.parseInt(aux[1]) - 1;
		int anos = Integer.parseInt(aux[2]) - 1970;
		return "" + ((dias > 0 ? dias + " dia" : "") + (dias > 1 ? "s" : "")
				+ (semanas > 0 ? " " + semanas + " semana" : "") + (semanas > 1 ? "s" : "")
				+ (meses > 0 ? " " + meses + " mes" : "") + (meses > 1 ? "es" : "")
				+ (anos > 0 ? " " + anos + " año" : "") + (anos > 1 ? "s" : "")).trim();
	}

	// Diferencia entre dos fechas, devuelve cantidad de dias
	private String desde(int dia, int mes, int ano) {
		Date actual = new Date();
		@SuppressWarnings("deprecation")
		Date FechaDada = new Date(ano - 1900, mes, dia);
		long Diferencia = FechaDada.getTime() - actual.getTime();
		if (Diferencia < 1)
			return "";
		Date desde = new Date(Diferencia);
		// return new SimpleDateFormat ("hh:mm:ss - dd/MM/yyyy").format(desde);
		// return "" + (desde.getTime() / (1000 * 60 * 60 * 24)) + " dias";
		// despues lo vemos esto
		String[] aux = new SimpleDateFormat("dd/MM/yyyy").format(desde).split("/");
		int dias = Integer.parseInt(aux[0]);
		int semanas = dias / 7;
		dias %= 7;
		int meses = Integer.parseInt(aux[1]) - 1;
		int anos = Integer.parseInt(aux[2]) - 1970;
		return "" + ((dias > 0 ? dias + " dia" : "") + (dias > 1 ? "s" : "")
				+ (semanas > 0 ? " " + semanas + " semana" : "") + (semanas > 1 ? "s" : "")
				+ (meses > 0 ? " " + meses + " mes" : "") + (meses > 1 ? "es" : "")
				+ (anos > 0 ? " " + anos + " año" : "") + (anos > 1 ? "s" : "")).trim();
	}

}
