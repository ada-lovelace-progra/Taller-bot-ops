package resolvedores;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import armadores.RespuestaGenerico;
import bdRespuestas.DeudasBD;

public class Deudas extends RespuestaGenerico {
	private Pattern regexAgendar = Pattern.compile("(\\S+):.*@(\\S+)\\D+me debe\\D+([0-9]+)");
	private Pattern regexAgendarLeDebo = Pattern.compile("(\\S+):\\D+([0-9]+)[^@]+@(\\S+)");
	private Pattern regexPagoAgendar = Pattern.compile("(\\S+):.*@(\\S+)\\D+me pag.\\D+([0-9]+)");
	private Pattern regexPagoAgendarInverso = Pattern.compile("(\\S+):.*@(\\S+)\\D+([0-9]+)");
	private Pattern regexCalcular = Pattern.compile("(\\w+):[^\\@]+@(\\w+)");
	private Pattern regexSimplificar = Pattern.compile("(\\w+):[^\\@]+@(\\w+)[^\\@]+@(\\w+)");
	private Pattern regexDeudasGrupales = Pattern.compile("(\\w+):|@(\\w+) ");

	@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			if (mensaje.matches(".*cu.nto me debe.*") || mensaje.matches(".*cu.nto le debo.*"))
				return calcularDeuda(mensaje, true);
			if (!mensaje.matches(".*cu.nto.*") && (mensaje.contains("me debe") || mensaje.contains("le debo")))
				return acreditarDeuda(mensaje);
			if (mensaje.contains("estado"))
				return estadoDeDeuda(mensaje);
			if (mensaje.contains("simplificar"))
				return simplificar(mensaje);
			if (mensaje.contains("gastamos"))
				return deudasGrupales(mensaje);
			if (mensaje.contains("me pag") || mensaje.contains("le pagu"))
				return acreditarPago(mensaje);
		}
		return null;
	}

	private String acreditarPago(String mensaje) {
		Matcher match = regexPagoAgendar.matcher(mensaje);
		String prestamista;
		String deudor;
		float monto;
		if (match.find()) {
			prestamista = match.group(2);
			deudor = match.group(1);
			monto = Float.parseFloat(match.group(3));
		} else {
			match = regexPagoAgendarInverso.matcher(mensaje);
			if (!match.find())
				return null;
			prestamista = match.group(1);
			deudor = match.group(2);
			monto = Float.parseFloat(match.group(3));
		}
		if (new DeudasBD().acreditarDeuda(prestamista, deudor, monto))
			return "Pago agendado";
		return "Pago no acreditado";
	}

	private String deudasGrupales(String mensaje) {
		Matcher match = regexDeudasGrupales.matcher(mensaje);
		ArrayList<String> deudores = new ArrayList<>();
		while (match.find()) {
			String group = match.group(1);
			if (group == null)
				deudores.add(match.group(2));
			else
				deudores.add(group);
		}
		Matcher quienPago = Pattern.compile("y pag..? @?(\\w+)").matcher(mensaje);
		String prestamista = null;
		if (quienPago.find())
			prestamista = quienPago.group(1);
		if (prestamista.equals("yo"))
			prestamista = deudores.get(0);
		while (deudores.contains(prestamista))
			deudores.remove(prestamista);

		Matcher numero = Pattern.compile("([0-9]+)").matcher(mensaje);
		if (!numero.find())
			return null;
		float valor = Float.parseFloat(numero.group(1)) / (deudores.size() + 1);
		DeudasBD db = new DeudasBD();
		for (String temp : deudores) {
			db.acreditarDeuda(prestamista, temp, valor);
		}
		return "gasto grupal anotado";
	}

	private String simplificar(String mensaje) {
		Matcher match = regexSimplificar.matcher(mensaje);
		if (!match.find())
			return null;
		DeudasBD bd = new DeudasBD();
		float deuda1, deuda2;
		String yo = match.group(1), deudor1 = match.group(2), deudor2 = match.group(3);
		deuda2 = bd.darDeBaja(yo, deudor2) - bd.darDeBaja(deudor2, yo);
		deuda1 = bd.darDeBaja(yo, deudor1) - bd.darDeBaja(deudor1, yo);
		if ((deuda1 > 0 && deuda2 < 0) || (deuda1 < 0 && deuda2 > 0)) {
			if (deuda1 > deuda2) {
				if (deuda1 > -deuda2) {
					bd.acreditarDeuda(yo, deudor1, deuda1 - deuda2);
					bd.acreditarDeuda(deudor1, deudor2, deuda2);
				} else {
					bd.acreditarDeuda(deudor2, yo, -(deuda2 + deuda1));
					bd.acreditarDeuda(deudor2, deudor1, deuda1);
				}
				return "listo.";
			} else if (deuda1 < deuda2) {
				if (deuda2 > -deuda1) {
					bd.acreditarDeuda(yo, deudor2, deuda2 - deuda1);
					bd.acreditarDeuda(deudor2, deudor1, deuda1);
				} else {
					bd.acreditarDeuda(deudor1, yo, -(deuda1 + deuda2));
					bd.acreditarDeuda(deudor1, deudor2, deuda2);
				}
				return "listo.";
			}
		}
		return "no tiene sentido simplificar";
	}

	private String calcularDeuda(String mensaje, boolean b) {
		Matcher match = regexCalcular.matcher(mensaje);
		if (!match.find())
			return null;
		String prestamista;
		String deudor;
		if (mensaje.contains("me debe")) {
			prestamista = match.group(1);
			deudor = match.group(2);
			float buscarDeuda = new DeudasBD().buscarDeuda(prestamista, deudor);
			return formateoDeben(prestamista, deudor, buscarDeuda)
					+ (b && buscarDeuda == 0 ? ", " + calcularDeuda(prestamista + ": @" + deudor, false) : "");
		} else {
			prestamista = match.group(2);
			deudor = match.group(1);
			float buscarDeuda = new DeudasBD().buscarDeuda(prestamista, deudor);
			return formateoDebo(prestamista, deudor, buscarDeuda)
					+ (b && buscarDeuda == 0 ? ", " + calcularDeuda(deudor + ": me debe @" + prestamista, false) : "");
		}
	}

	private String formateoDebo(String prestamista, String deudor, float buscarDeuda) {
		if (buscarDeuda == 0)
			return "No le debes nada a @" + prestamista;
		return "le debés $" + (buscarDeuda % 1 == 0 ? String.format("%d", (int) buscarDeuda) : buscarDeuda) + " a @"
				+ prestamista;
	}

	private String formateoDeben(String prestamista, String deudor, float buscarDeuda) {
		if (buscarDeuda == 0)
			return "@" + deudor + " no te debe nada";
		return "@" + deudor + " te debe $"
				+ (buscarDeuda % 1 == 0 ? String.format("%d", (int) buscarDeuda) : buscarDeuda);
	}

	private String acreditarDeuda(String mensaje) {
		Matcher match = regexAgendar.matcher(mensaje);
		String prestamista;
		String deudor;
		float monto;
		if (match.find()) {
			prestamista = match.group(1);
			deudor = match.group(2);
			monto = Float.parseFloat(match.group(3));
		} else {
			match = regexAgendarLeDebo.matcher(mensaje);
			if (!match.find())
				return null;
			prestamista = match.group(3);
			deudor = match.group(1);
			monto = Float.parseFloat(match.group(2));
		}
		if (new DeudasBD().acreditarDeuda(prestamista, deudor, monto))
			return "Deuda agendada";

		return "Deuda no acreditada";
	}

	private String estadoDeDeuda(String mensaje) {
		String miNombre = mensaje.substring(0, mensaje.indexOf(":"));
		String salida = "";
		ArrayList<DeudasBD> lista = new DeudasBD().buscarDeuda(miNombre);
		if (lista == null)
			return "No tenes deudas registradas";
		for (DeudasBD temp : lista)
			if (temp.getPrestamista().equals(miNombre))
				salida += formateoDeben(temp.getPrestamista(), temp.getDeudor(), temp.getMonto()) + ", ";
			else
				salida += formateoDebo(temp.getPrestamista(), temp.getDeudor(), temp.getMonto()) + ", ";

		salida = salida.trim();
		salida = salida.substring(0, salida.length() - 1);
		return salida;
	}
}
