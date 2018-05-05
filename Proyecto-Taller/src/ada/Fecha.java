package ada;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Fecha {

	// devuelve fecha actual en formato hora:min:seg dia/mes/año
	static public String now() {
		return new SimpleDateFormat("hh:mm:ss - dd/MM/yyyy").format(new Date());
	}

	// devuelve hora:minutos
	static public String getHora() {
		return new SimpleDateFormat("HH:mm").format(new Date());
	}

	// devuelve fecha actual en formato dia/mes/año
	static public String getFecha() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	// devuelve la fecha actual en formato "viernes, 04 de mayo de 2018"
	static public String getFechaCompleta() {
		return new SimpleDateFormat("EEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy").format(new Date());
	}

	// Devuelve dia de la semana actual
	static public String getDiaDeLaSemana() {
		return new SimpleDateFormat("EEEEEEEEE").format(new Date());
	}

	// Diferencia entre dos fechas, devuelve cantidad de dias
	static public String hasta(int dia, int mes, int ano) {
		Date actual = new Date();
		GregorianCalendar FechaDada = new GregorianCalendar(ano, mes, dia);
		Date hasta = new Date(FechaDada.getTimeInMillis() - actual.getTime());
		// return new SimpleDateFormat ("hh:mm:ss - dd/MM/yyyy").format(hasta);
		int dias = (int) (hasta.getTime() / (1000 * 60 * 60 * 24));
		int semanas = dias % 28 / 7;
		int meses = dias % 365 / 12;
		int anos = dias / 365;
		return "" + (dias > 0 ? dias + " dias" : "") + (semanas > 0 ? semanas + " dias" : "")
				+ (meses > 0 ? meses + " dias" : "") + (anos > 0 ? anos + " dias" : "");
	}

	// Diferencia entre dos fechas, devuelve cantidad de dias
	static public String desde(int dia, int mes, int ano) {
		Date actual = new Date();
		GregorianCalendar FechaDada = new GregorianCalendar(ano, mes, dia);
		Date desde = new Date(actual.getTime() - FechaDada.getTimeInMillis());
		// return new SimpleDateFormat ("hh:mm:ss - dd/MM/yyyy").format(desde);
		//return "" + (desde.getTime() / (1000 * 60 * 60 * 24)) + " dias";

		int dias = (int) (desde.getTime() / (1000 * 60 * 60 * 24));
		int semanas = dias % 28 / 7;
		int meses = dias % 365 / 12;
		int anos = dias / 365;
		return "" + (dias > 0 ? dias + " dias" : "") + (semanas > 0 ? semanas + " dias" : "")
				+ (meses > 0 ? meses + " dias" : "") + (anos > 0 ? anos + " dias" : "");	}

}
