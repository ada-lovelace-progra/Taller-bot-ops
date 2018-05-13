package resolvedores;

public class Llamada extends RespuestaGenerico {

	public Llamada() {
		super();
		nombreDeLaClase = "llamadas";
		// utilizo esto mas que nada para poder leer los archivos de peticion y
		// respuesta... despues vuela con SQL seguramente... A menos que.....
	}

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return respuesta();

		return null;
	}
}
