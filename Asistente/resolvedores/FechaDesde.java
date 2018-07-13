package resolvedores;

import java.util.regex.Matcher;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class FechaDesde extends FechaGenerico {
	
	public FechaDesde(String s) {
		super(s);
	}

	public FechaDesde() {
		super();
	}
	
	
	@Override
	public String request(String mensaje) {
		if (mensaje.contains("paso"))
			return handle(mensaje);
		else if (this.siguiente != null) {
			return this.siguiente.request(mensaje);
		} else
			return null;
	}

	@Override
	public String handle(String mensaje) {
		int dia=1, mes=1, ano=2000;
		Matcher regexFechaCompleta = patternFechaCompleta.matcher(mensaje);
		if (regexFechaCompleta.find()) {
			dia = Integer.parseInt(regexFechaCompleta.group(1));
			mes = Integer.parseInt(regexFechaCompleta.group(2));
			ano = Integer.parseInt(regexFechaCompleta.group(3));
		}
		DateTime startDate = new DateTime(ano, mes, dia, 0, 0, 0, 0);
		DateTime endDate = (this.fecha == null) ? new DateTime() : this.fecha;
		Days d = Days.daysBetween(startDate, endDate);
		int days = d.getDays();
		return "" + days + " dias";
	}

}
