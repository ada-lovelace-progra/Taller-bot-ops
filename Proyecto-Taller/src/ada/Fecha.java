package ada;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


public class Fecha {
	static public String now() {
		return new SimpleDateFormat ("hh:mm:ss - dd/MM/yyyy").format(new Date());
	}
	
	static public String hasta(int dia, int mes, int ano) {
		Date actual = new Date();
		GregorianCalendar FechaDada = new GregorianCalendar(ano,mes,dia);
		Date hasta= new Date(FechaDada.getTimeInMillis()-actual.getTime());
		return new SimpleDateFormat ("hh:mm:ss - dd/MM/yyyy").format(hasta);
	}
	
	static public String desde(int dia, int mes, int ano) {
		Date actual = new Date();
		GregorianCalendar FechaDada = new GregorianCalendar(ano,mes,dia);
		Date Desde= new Date(actual.getTime()-FechaDada.getTimeInMillis());
		return new SimpleDateFormat ("hh:mm:ss - dd/MM/yyyy").format(Desde);
	}
}
