package resolvedores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import armadores.RespuestaGenerico;
import bdRespuestas.DeudasBD;

public class Deudas extends RespuestaGenerico {
	private Pattern regexAgendar = Pattern.compile("(\\S+):.*@(\\S+)\\D+me debe\\D+([0-9]+)");
	private Pattern regexAgendarLeDebo = Pattern.compile("(\\S+):\\D+([0-9]+)[^@]+@(\\S+)");
	private Pattern regexCalcular = Pattern.compile("(\\w+):[^\\@]+@(\\w+)");

	@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			if (mensaje.matches(".*cu.nto me debe.*"))
				return "@" + mensaje.substring(0, mensaje.indexOf(":")) + ", " + calcularDeuda(mensaje);
			if (mensaje.contains("me debe") || mensaje.contains("le debo"))
				return acreditarDeuda(mensaje);
		}
		return null;
	}

	private String calcularDeuda(String mensaje) {
		Matcher match = regexCalcular.matcher(mensaje);
		if (!match.find())
			return null;
		String prestamista;
		String deudor;
		if (mensaje.contains("me debe")) {
			prestamista = match.group(1);
			deudor = match.group(2);
			float buscarDeuda = new DeudasBD().buscarDeuda(prestamista, deudor);
			return "@" + deudor + " te debe $"
					+ (buscarDeuda % 1 == 0 ? String.format("%d", (int) buscarDeuda) : buscarDeuda);
		} else {
			prestamista = match.group(2);
			deudor = match.group(1);
			float buscarDeuda = new DeudasBD().buscarDeuda(prestamista, deudor);
			return "le debés $" + (buscarDeuda % 1 == 0 ? String.format("%d", (int) buscarDeuda) : buscarDeuda) + "a @"
					+ prestamista;
		}
	}

	private String acreditarDeuda(String mensaje) {
		Matcher match = regexAgendar.matcher(mensaje);
		if (match.find()) {
			String prestamista;
			String deudor;
			prestamista = match.group(1);
			deudor = match.group(2);
			float monto = Float.parseFloat(match.group(3));
			if (new DeudasBD().acreditarDeuda(prestamista, deudor, monto))
				return "Deuda agendada";
		} else {
			match = regexAgendarLeDebo.matcher(mensaje);
			if (!match.find())
				return null;
			String prestamista;
			String deudor;
			prestamista = match.group(3);
			deudor = match.group(1);
			float monto = Float.parseFloat(match.group(2));
			if (new DeudasBD().acreditarDeuda(prestamista, deudor, monto))
				return "Deuda agendada";
		}

		return "Deuda no acreditaa";
	}
}
