package ada;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


public class Fecha {
	
	//devuelve fecha actual en formato hora:min:seg dia/mes/año 
	static public String now() {
		return new SimpleDateFormat ("hh:mm:ss - dd/MM/yyyy").format(new Date());
	}
	
	//devuelve hora:minutos
	static public String getHora() {
		Date hora = new Date();
		return "" + hora.getHours() + ":" + hora.getMinutes();
	}
	
	//devuelve fecha actual en formato dia/mes/año
	static public String getFecha() {
		return new SimpleDateFormat ("dd/MM/yyyy").format(new Date());
	}
	
	//devuelve el dia de la variable date que recibe
	static public String getDia( Date dia) {
		switch ( dia.getDay() )
		{
		case 1:	return "Lunes";
		case 2:	return "Martes";
		case 3:	return "Miercoles";
		case 4:	return "Jueves";
		case 5:	return "Viernes";
		case 6:	return "Sabado";
		case 7:	return "Domingo";		
		}
	return "";
	}
	
	//Devuelve dia de la semana actual
	static public String getDiaDeLaSemana() {
		Date dia = new Date();
		switch ( dia.getDay() )
		{
		case 1:	return "Lunes";
		case 2:	return "Martes";
		case 3:	return "Miercoles";
		case 4:	return "Jueves";
		case 5:	return "Viernes";
		case 6:	return "Sabado";
		case 7:	return "Domingo";		
		}
	return "";
	}
	
	// Diferencia entre dos fechas, devuelve cantidad de dias
	static public String hasta(int dia, int mes, int ano) {
		Date actual = new Date();
		GregorianCalendar FechaDada = new GregorianCalendar(ano,mes,dia);
		Date hasta= new Date(FechaDada.getTimeInMillis()-actual.getTime());
		//return new SimpleDateFormat ("hh:mm:ss - dd/MM/yyyy").format(hasta);
		return "" + (hasta.getTime() / (1000 * 60 * 60 * 24)) + " dias";
	}
	
	// Diferencia entre dos fechas, devuelve cantidad de dias
	static public String desde(int dia, int mes, int ano) {
		Date actual = new Date();
		GregorianCalendar FechaDada = new GregorianCalendar(ano,mes,dia);
		Date desde= new Date(actual.getTime()-FechaDada.getTimeInMillis());
		//return new SimpleDateFormat ("hh:mm:ss - dd/MM/yyyy").format(desde);
		return "" + (desde.getTime() / (1000 * 60 * 60 * 24)) + " dias";
	}

}
