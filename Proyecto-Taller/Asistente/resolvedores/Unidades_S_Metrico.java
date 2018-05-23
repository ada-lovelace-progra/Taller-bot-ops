package resolvedores;

public class Unidades_S_Metrico extends RespuestaGenerico {

	@Override
	public String intentarResponder(String mensaje) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String cambio(String cad) {
		String regex = "(gramo)?(litro)?(metro)?+s?";
		if (cad.matches("[0-9]+ .*")) {
			double parseDouble = Double.parseDouble(cad.substring(0, cad.indexOf(" ")));
			parseDouble = loLlevoABase(cad, regex, parseDouble);
			return deBaseALaUnidadPedida(cad, regex, parseDouble) + unidad(cad);
		}
		return "";
	}

	private static String deBaseALaUnidadPedida(String cad, String regex, double parseDouble) {
		if (cad.matches(".*(kilo)" + regex)) {
			parseDouble /= 1000;
			cad = parseDouble + " K";
		} else if (cad.matches(".*(hecto)" + regex)) {
			parseDouble /= 100;
			cad = parseDouble + " H";
		} else if (cad.matches(".*(deca)" + regex)) {
			parseDouble /= 10;
			cad = parseDouble + " D";
		} else if (cad.matches(".*(deci)" + regex)) {
			parseDouble *= 10;
			cad = parseDouble + " d";
		} else if (cad.matches(".*(centi)" + regex)) {
			parseDouble *= 100;
			cad = parseDouble + " c";
		} else if (cad.matches(".*(mili)" + regex)) {
			parseDouble *= 1000;
			cad = parseDouble + " m";
		} else
			cad = parseDouble + " ";
		return cad;
	}

	private static String unidad(String cad) {
		if (cad.contains("litro"))
			return "l";
		if (cad.contains("gramo"))
			return "g";
		if (cad.contains("metro"))
			return "m";
		return "";
	}

	private static double loLlevoABase(String cad, String regex, double parseDouble) {
		regex += ".*";
		if (cad.matches("[0-9]+ (kilo)" + regex))
			parseDouble *= 1000;
		else if (cad.matches("[0-9]+ (hecto)" + regex))
			parseDouble *= 100;
		else if (cad.matches("[0-9]+ (deca)" + regex))
			parseDouble *= 10;
		else if (cad.matches("[0-9]+ (deci)" + regex))
			parseDouble /= 10;
		else if (cad.matches("[0-9]+ (centi)" + regex))
			parseDouble /= 100;
		else if (cad.matches("[0-9]+ (mili)" + regex))
			parseDouble /= 1000;
		return parseDouble;
	}
}
