package funcionesExtras;

public class Cambio_Unidades {
	static public void main(String a[]) {
		System.out.println(cambio());
	}

	private static String cambio() {
		String cad = "3 kilos a centilitro";
		String regex = "(gramo)?(litro)?(metro)?+s?";
		if (cad.matches("[0-9].*")) {
			double parseDouble = Double.parseDouble(cad.substring(0, cad.indexOf(" ")));
			if (cad.matches("[0-9]+ kilo.*")) {
				if (cad.matches(".*(deci)" + regex))
					cad = parseDouble * 1000 * 10 + " d";
				else if (cad.matches(".*(centi)" + regex))
					cad = parseDouble * 1000 * 100 + " c";
				else if (cad.matches(".*(mili)" + regex))
					cad = parseDouble * 1000 * 1000 + " m";
				else if (cad.matches(".*" + regex))
					cad = parseDouble * 1000 + " ";
			}
		}
		return cad;
	}
}
