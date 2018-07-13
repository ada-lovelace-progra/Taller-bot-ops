package fecha;

import java.util.regex.Matcher;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class FechaDentroDe extends FechaGenerico {
	public FechaDentroDe(String s) {
		super(s);
	}

	public FechaDentroDe() {
		super();
	}
	
	@Override
	public String request(String mensaje) {
		if (mensaje.contains("dentro"))
			return handle( mensaje);
		else if( this.siguiente != null ) {
			return this.siguiente.request(mensaje);
		}else
			return null;
	}
	
	@Override
	public String handle(String mensaje) {
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
		return null;
	}

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
}
