package pendientes;

import resolvedores.RespuestaGenerico;

public class Agradecer extends RespuestaGenerico {

	public Agradecer() {
		super();
	}

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return "De nada";
		return null;
	}

}
