package resolvedores;

import java.io.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RecordarEventos extends RespuestaGenerico {

	public RecordarEventos() {
	}

	public String intentarResponder(String mensaje)  {
		if (consulta(mensaje)) 
		{
			if((mensaje.matches(".*" + "agrega" + ".*"))) 	//identifico que el pedido es agregar un evento
			{
				mensaje=mensaje.substring(mensaje.indexOf(":")+1);
				return	agregarEvento(mensaje.substring(mensaje.indexOf(":")+1));//solo le pasa la fecha del evento
			
			}
			else if(mensaje.matches(".*" + "proximo" + ".*"))
			{
				return proximoEvento();
			}
		}
													

		return null;

	}
	private String proximoEvento() {
		Scanner scan = null;
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		Date evento;
		int diasmenor=0,diferencia;
		String nextLine;
		String aux1,aux2=null;
		try {
			scan = new Scanner (new File("Respuestas\\respuestas_RecordarEventos.dat"));
			Date hoy =dateFormat.parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
			
			if(scan.hasNextLine()) //por si no hay eventos
			{
				//seteo al primer evento como el mas proximo
				 nextLine= scan.nextLine();
				aux2=nextLine;
				nextLine =nextLine.substring(nextLine.indexOf("/")-1);
				evento =dateFormat.parse(nextLine);
				diasmenor=(int) ((evento.getTime()-hoy.getTime())/86400000);//esto es la diferencia de dias del evento a la fecha actual
				while(scan.hasNextLine())
			{
				nextLine= scan.nextLine();
				aux1=nextLine;
				nextLine =nextLine.substring(nextLine.indexOf("/")-1);
				evento =dateFormat.parse(nextLine);
				diferencia=(int) ((evento.getTime()-hoy.getTime())/86400000);
				 if(diferencia < diasmenor) 
				 {
					 diasmenor=diferencia;
					aux2=aux1;
				 }
			}
			
			if(scan!=null)
        			scan.close();
			return "El proximo evento es ("+aux2+") y falta/n "+" ("+diasmenor+") dias";
			}
		}catch(Exception e) { e.printStackTrace();}
		
		return "No hay eventos registrados";
	}

	private String agregarEvento(String evento)
	{
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("Respuestas\\respuestas_RecordarEventos.dat",true);
            pw = new PrintWriter(fichero);
            evento=evento.trim();
            pw.println(evento);
	}catch(Exception e){e.printStackTrace();}
        finally 
        {
        	try {
        		if(pw!=null)
        			pw.close();
        	}catch(Exception e2) {e2.printStackTrace();}
        }
        String aux="Evento agregado";
        return aux;

     }

	
	
}
