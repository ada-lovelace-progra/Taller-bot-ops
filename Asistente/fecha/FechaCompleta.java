package fecha;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaCompleta extends FechaGenerico {
	@Override
	public String request(String mensaje) {
		if (mensaje.contains("semana"))
			return handle(mensaje);
		else if (this.siguiente != null) {
			return this.siguiente.request(mensaje);
		} else
			return null;
	}

	@Override
	public String handle(String mensaje) {
		return new SimpleDateFormat("EEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy")
				.format((this.fecha2 == null) ? new Date() : this.fecha2);
	}
	
	public FechaCompleta(String s) {
		super(s);
	}

	public FechaCompleta() {
		super();
	}

}
