package resolvedores;

public class Simpsons extends RespuestaGenerico {

	public Simpsons() {
		super();
	}

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return respuesta();
		return null;
	}

}
