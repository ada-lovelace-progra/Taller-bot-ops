package fecha;

import resolvedores.Fecha;

public abstract class FechaGenerico extends Fecha {
	protected FechaGenerico siguiente  = null;

	public FechaGenerico(String s) {
		super(s);
	}
	
	public FechaGenerico() {
		super();
	}
	
	public abstract String request(String mensaje);	
	public abstract String handle(String mensaje);//puFede que tenga que ser protected, no estoy seguro
	
	public void siguiente(FechaGenerico sig) {
		this.siguiente = sig;
	}
}
