package resolvedores;


/** 
 * Resolvedor, tira "datos" de chuck norris.
 */
public class ChuckNorris extends RespuestaGenerico {

	private int id;
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public ChuckNorris() {
		super();
	}

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			return obtenerChuckFact();
		}
		return Siguiente.intentarResponder(mensaje);
	}

	public String obtenerChuckFact()
	{
		return (new ChuckFact()).obtenerChuckFact();		
	}	

}
