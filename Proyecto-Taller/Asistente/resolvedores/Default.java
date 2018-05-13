package resolvedores;

public class Default extends RespuestaGenerico {

	public Default() {
		super(true);
		nombreDeLaClase = "Default";
		// utilizo esto mas que nada para poder leer los archivos de peticion y
		// respuesta... despues vuela con SQL seguramente... A menos que.....
	}

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return respuesta();
		return null;
	}
}
