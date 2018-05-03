package ada;

public class CalculoString {
	private int cantChar(char a, String cad) {
		int i = 0, j = cad.length();
		while (j-- != 0)
			if (cad.charAt(j) == a)
				i++;
		return i;
	}

	public String calcularFormat(String funcion, String formato) {
		try {
			double var = calcular(funcion);
			return var % 1 == 0 ? ((int) var) + "" : String.format(formato, var);
		} catch (Exception e) {
			return "";
		}
	}

	public double calcular(String cad) {
		while (cad.contains("(")) {
			int ini = cad.indexOf("(") + 1;
			int fin = cad.lastIndexOf(")");
			String subcad = cad.substring(ini, fin);
			String restocad = cad.substring(fin + 1);
			while (cantChar('(', subcad) != cantChar(')', subcad)) {
				fin = restocad.indexOf(")");
				if (fin != restocad.length())
					restocad = restocad.substring(fin + 1);
				subcad += cad.substring(subcad.length(), fin);
			}
			cad = cad.substring(0, ini - 1) + calcular(subcad) + restocad;
		}

		if (cad.contains("+")) {
			int div = cad.indexOf("+");
			if (div != 0)
				return calcular(cad.substring(0, div)) + calcular(cad.substring(div + 1));
			return calcular(cad.substring(div + 1));
		}

		if (cad.contains("-") && cad.lastIndexOf("-") != 0 && Character.isDigit(cad.charAt(cad.lastIndexOf("-") - 1))) {
			int div = cad.lastIndexOf("-");
			if (div != 0)
				return calcular(cad.substring(0, div)) - calcular(cad.substring(div + 1));
			return -calcular(cad.substring(div + 1));
			/*
			 * double izq = calcular(cad.substring(0, div)); int fin = 0; double num = 0;
			 * while (cad.length() - 1 > fin + div && Character.isDigit(cad.substring(div +
			 * 1).charAt(fin))) { fin++; } num = -1 * Double.parseDouble(cad.substring(div +
			 * 1, div + 1 + fin)); cad = (izq + num) + cad.substring(div + fin + 1);
			 * 
			 * return calcular(cad);
			 */
		}

		if (cad.contains("/")) {
			int div = cad.lastIndexOf("/");
			if (div != 0)
				return calcular(cad.substring(0, div)) / calcular(cad.substring(div + 1));
			return 0;
		}

		if (cad.contains("*")) {
			int div = cad.indexOf("*");
			if (div != 0)
				return calcular(cad.substring(0, div)) * calcular(cad.substring(div + 1));
			return 0;
		}

		if (cad.contains("^")) {
			int div = cad.indexOf("^");
			if (div != 0)
				return Math.pow(calcular(cad.substring(0, div)), calcular(cad.substring(div + 1)));
			return 0;
		}

		return cad.matches("L?l?og[0-9]+\\.?[0-9]*") ? Math.log(Double.parseDouble(cad.substring(3, cad.length() - 1)))
				: Double.parseDouble(cad);
	}
}
