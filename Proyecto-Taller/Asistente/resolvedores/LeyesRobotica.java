package resolvedores;


/** 
 * Resolvedor, son las leyes de la robotica.
 */
public class LeyesRobotica extends RespuestaGenerico {

	String resp = "1- Un robot no debe dañar a un ser humano o, por su inacción, dejar que un ser humano sufra daño.\r\n"
			+ "2- Un robot debe obedecer las órdenes que le son dadas por un ser humano, excepto si estas órdenes entran en conflicto con la Primera Ley.\r\n"
			+ "3- Un robot debe proteger su propia existencia, hasta donde esta protección no entre en conflicto con la Primera o la Segunda Ley.";

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return resp;
		return null;
	}

}
