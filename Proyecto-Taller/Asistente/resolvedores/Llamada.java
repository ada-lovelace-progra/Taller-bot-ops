package resolvedores;


public class Llamada extends RespuestaGenerico {

	public Llamada() {
		super(true);
	}

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return respuesta();
		return null;
	}
}
