package armadores;

import java.util.ArrayList;

import bdRespuestas.BaseDato;
import bdRespuestas.RespuestaBD;

/**
 * Todas las respuestas de ada deberan extender de esta clase, es el "Handler"
 * del asistente.
 */
public abstract class RespuestaGenerico {

	protected RespuestaGenerico Siguiente = null;
	private ArrayList<RespuestaBD> peticiones = null;
	public String respuesta;
	protected static double cordialidad;
	public static String nombre;

	public RespuestaGenerico() {
		Siguiente = new Default();
	}

	public RespuestaGenerico(boolean op) {
	}

	public void siguiente(RespuestaGenerico sig) {
		this.Siguiente = sig;
	}

	public String intentar(String mensaje) {
		String temp = intentarResponder(mensaje);
		if (temp != null)
			return temp;
		else if (Siguiente != null) // esto va a ser null cuando es la clase default... la cual es la ultima
			return Siguiente.intentar(mensaje);
		else
			return null;
	}

	public abstract String intentarResponder(String mensaje);

	protected boolean consulta(String mensaje) {
		if (peticiones == null)
			cargarLista(this.getClass().getSimpleName());
		if (peticiones != null) {
			int index = -1;
			for (RespuestaBD temp : peticiones) {
				String corregido = temp.peticion.replace("@asistente", ".*@" + nombre).toLowerCase();
				corregido = corregido.replaceAll(" ", ".*");
				index++;
				if (mensaje.matches(".*" + corregido + ".*")) {
					this.respuesta = this.peticiones.get(index).respuesta;
					return true;
				}
			}
		}
		return false;
	}

	private void cargarLista(String clase) {
		this.peticiones = new BaseDato().traerDatos(clase);
	}
}
