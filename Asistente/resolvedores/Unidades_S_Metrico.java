package resolvedores;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import armadores.RespuestaGenerico;
import bdRespuestas.UnidadesSMBD;

/** 
 * Resolvedor, Convierte entre distintas unidades.
 * 
 * lb libra
 * oz ozna
 * st stone
 * kg kilogramo
 * gr gramo
 * tn tonelada
 * in pulgada
 * ft pie
 * yd yarda
 * m metro
 * km kilometro
 * cm centimetro
 * cu in pulgada cubica
 * pt pinta
 * gal galon
 * l litro
 * ml mililitro
 * m3 metro cubico
 * ms milisegundo
 * s segundo 
 * min minuto
 * h hora
 */
public class Unidades_S_Metrico extends RespuestaGenerico {

	//@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			return cambio((mensaje.replace(this.respuesta.toLowerCase(), "")).trim());
		}
		return null;
	}

	public String cambio(String entrada) {
		Matcher regex = Pattern.compile("([0-9]+[0-9]*) (\\S+) [a-z] (\\S+)").matcher(entrada);
		String unidadLLegada = null;
		String unidadDestino = null;
		double valor = 0;
		if (regex.find()) {
			unidadLLegada = regex.group(2);
			unidadDestino = regex.group(3);
			valor = Double.parseDouble(regex.group(1));
			return new UnidadesSMBD().convertir(unidadLLegada, unidadDestino, valor);
		}		
		return  "No se pudo realizar la conversión";
	}
}

