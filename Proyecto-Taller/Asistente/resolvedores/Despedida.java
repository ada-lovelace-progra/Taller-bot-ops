package resolvedores;


/**
 * Resolvedor, el asistente se despide.
 */
public class Despedida extends RespuestaGenerico {

	public Despedida() {
		super();
	}

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return "-1-2" + respuesta();
		return null;
	}

}
