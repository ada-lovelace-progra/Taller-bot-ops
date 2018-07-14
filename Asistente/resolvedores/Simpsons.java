package resolvedores;

import armadores.RespuestaGenerico;
import bdRespuestas.SimpsonsBD;

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
//		if(mensaje.matches(".*simpsons.*"))
//			return getSimpsons();
		return null;
	}
	
	public String getSimpsons()
	{
		return new SimpsonsBD().getSimpsonsCaps();		
	}
}
