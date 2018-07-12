package resolvedores;

import armadores.RespuestaGenerico;

/** 
 * Resolvedor, agradece.
 */
public class Agradecer extends RespuestaGenerico {

	public Agradecer() {
		super();
	}

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return respuesta;
		return null;
	}

}
