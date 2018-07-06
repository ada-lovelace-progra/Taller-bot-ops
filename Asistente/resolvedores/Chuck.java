package resolvedores;

import armadores.RespuestaGenerico;
import bdRespuestas.ChuckBD;

/** 
 * Resolvedor, tira "datos" de chuck norris.
 */
public class Chuck extends RespuestaGenerico {

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			return obtenerChuckFact();
		}
		return Siguiente.intentarResponder(mensaje);
	}

	public String obtenerChuckFact()
	{
		return (new ChuckBD()).obtenerChuckFact();		
	}	

}
