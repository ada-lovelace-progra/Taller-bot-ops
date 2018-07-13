package resolvedores;


import java.util.regex.Matcher;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class FechaHaceQue extends FechaGenerico {
	public FechaHaceQue(String s) {
		super(s);
	}

	public FechaHaceQue() {
		super();
	}
	
	@Override
	public String request(String mensaje) {
		if (mensaje.contains("hace"))
			return handle(mensaje);
		else if (this.siguiente != null) {
			return this.siguiente.request(mensaje);
		} else
			return null;
	}

	@Override
	public String handle(String mensaje) {
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
		return null;
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

}
