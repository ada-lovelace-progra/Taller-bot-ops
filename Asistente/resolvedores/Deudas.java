package resolvedores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import armadores.RespuestaGenerico;
import bdRespuestas.DeudasBD;

public class Deudas extends RespuestaGenerico {
	private Pattern regex = Pattern.compile("[0-9]+(\\S+):.*@(\\S+).* ([0-9]+)");

	@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return acreditarDeuda(mensaje);
		return null;
	}

	public String acreditarDeuda(String mensaje) {
		if(mensaje.contains("me debe")){
			Matcher match = regex.matcher(mensaje);
			if (!match.find())
				return null;
			String prestamista = mensaje.substring(4, mensaje.indexOf(":"));
			String deudor = match.group(2);
			double monto = Double.parseDouble(match.group(3));
			if (new DeudasBD().acreditarDeuda(prestamista, deudor, monto))
				return "Deuda acreditada";

			return "Deuda no acreditaa";
		}
		return null;
	}
}
