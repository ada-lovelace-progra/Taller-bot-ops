package pendientes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import resolvedores.RespuestaGenerico;

public class Unidades_S_Metrico extends RespuestaGenerico {

	@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			return cambio(mensaje);
		}
		return null;
	}

	public static String cambio(String entrada) {
		Matcher asd = Pattern.compile("([0-9]+) ?(\\S+) a (\\S+)").matcher(entrada);
		String unidadLLegada = null;
		String unidadDestino = null;
		int valor = 0;
		if (asd.find()) {
			unidadLLegada = asd.group(2);
			unidadDestino = asd.group(3);
			valor = Integer.parseInt(asd.group(1));

		}
		return valor + unidadDestino + unidadLLegada;
	}
}
