package resolvedores;

import armadores.RespuestaGenerico;

/** 
 * linguo muerto.
 */
public class Simpsons extends RespuestaGenerico {

	public Simpsons() {
		super();
	}

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return respuesta;
		if(mensaje.matches(".*simpsons.*"))
			return getSimpsons();
		return null;
	}
	
	public String getSimpsons()
	{
		return new SimpsonsCaps().getSimpsonsCaps();		
	}
}
