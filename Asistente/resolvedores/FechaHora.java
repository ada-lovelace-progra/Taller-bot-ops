package resolvedores;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaHora extends FechaGenerico {
	
	public FechaHora(String s) {
		super(s);
	}

	public FechaHora() {
		super();
	}
	
	@Override
	public String request(String mensaje) {
		if (mensaje.contains("hora"))
			return handle( mensaje);
		else if( this.siguiente != null ) {
			return this.siguiente.request(mensaje);
		}else
			return null;
	}
	
	@Override
	public String handle(String mensaje) {
		return new SimpleDateFormat("HH:mm").format((this.fecha2 == null) ? new Date() : this.fecha2);
	}
	
}
