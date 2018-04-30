package ada;

public class CalculoString {
	private static int cantChar(char a, String cad) {
		int i = 0, j = cad.length();
		while (j-- != 0)
			if (cad.charAt(j) == a)
				i++;
		return i;
	}

	public static String calcularFormat(String funcion, String formato) {
		try {
			return String.format(formato, calcular(funcion));
		} catch (Exception e) {
			return "";
		}
	}

	public static double calcular(String cad) {
		while (cad.contains("(")) {
			int ini = cad.indexOf("(") + 1;
			int fin = cad.indexOf(")");
			String subcad = cad.substring(ini, fin);
			String restocad = cad.substring(fin + 1);
			while (cantChar('(', subcad) != cantChar(')', subcad)) {
				fin = restocad.indexOf(")");
				if (fin != restocad.length())
					restocad = restocad.substring(fin + 1);
				subcad += cad.substring(subcad.length(), fin);
			}
			cad = cad.substring(0, ini - 1) + calcular(subcad) + restocad;
			// System.out.println(cad);
		}

		if (cad.contains("+")) {
			int div = cad.indexOf("+");
			return calcular(cad.substring(0, div)) + calcular(cad.substring(div + 1));
		}
		if (cad.contains("-")) {
			int div = cad.indexOf("-");
			return calcular(cad.substring(0, div)) - calcular(cad.substring(div + 1));
		}
		if (cad.contains("*")) {
			int div = cad.indexOf("*");
			return calcular(cad.substring(0, div)) * calcular(cad.substring(div + 1));
		}
		if (cad.contains("/")) {
			int div = cad.indexOf("/");
			return calcular(cad.substring(0, div)) / calcular(cad.substring(div + 1));
		}

		if (cad.contains("^")) {
			int div = cad.indexOf("^");
			return Math.pow(calcular(cad.substring(0, div)), calcular(cad.substring(div + 1)));
		}
		return Double.parseDouble(cad);
	}
}
