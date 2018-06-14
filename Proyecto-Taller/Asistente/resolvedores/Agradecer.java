package resolvedores;

public class Agradecer extends RespuestaGenerico {

	public Agradecer() {
		super();
	}

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return respuesta();
		return null;
	}

}

/**Estoy probando para hacer un commit. Manu **/
//prueba de commit - Quimey