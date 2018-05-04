package ada;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Fecha {
	static public String now() {
		return new SimpleDateFormat ("hh:mm:ss - dd/MM/yyyy").format(new Date());
	}
}
