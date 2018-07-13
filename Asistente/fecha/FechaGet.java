package fecha;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaGet extends FechaGenerico {
	public FechaGet(String s) {
		super(s);
	}

	public FechaGet() {
		super();
	}

	@Override
	public String request(String mensaje) {
		if (mensaje.contains("getfecha"))
			return handle(mensaje);
		else if (this.siguiente != null) {
			return this.siguiente.request(mensaje);
		} else
			return null;
	}

	@Override
	public String handle(String mensaje) {
		return new SimpleDateFormat("dd/MM/yyyy").format((this.fecha2 == null) ? new Date() : this.fecha2);
	}

}
