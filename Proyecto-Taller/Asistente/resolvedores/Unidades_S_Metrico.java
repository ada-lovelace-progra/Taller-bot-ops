package resolvedores;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * Resolvedor, Convierte entre distintas unidades.
 * 
 * lb libra
 * oz ozna
 * st stone
 * kg kilogramo
 * gr gramo
 * tn tonelada
 * in pulgada
 * ft pie
 * yd yarda
 * m metro
 * km kilometro
 * cm centimetro
 * cu in pulgada cubica
 * pt pinta
 * gal galon
 * l litro
 * ml mililitro
 * m3 metro cubico
 * ms milisegundo
 * s segundo 
 * min minuto
 * h hora
 */
public class Unidades_S_Metrico extends RespuestaGenerico {
	
	private Hashtable<String, Double> masaSA=new Hashtable<String, Double>();
	private Hashtable<String, Double> longitudSA=new Hashtable<String, Double>();
	private Hashtable<String, Double> capacidadSA=new Hashtable<String, Double>();
	private Hashtable<String, Double> masaSI=new Hashtable<String, Double>();
	private Hashtable<String, Double> longitudSI=new Hashtable<String, Double>();
	private Hashtable<String, Double> capacidadSI=new Hashtable<String, Double>();
	private Hashtable<String, Double> tiempo=new Hashtable<String, Double>();
	private Hashtable<String, Double> conversion=new Hashtable<String, Double>();
	private ArrayList<String> abreviacion = new ArrayList<String>();

	//@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			return cambio(mensaje);
		}
		return null;
	}

	public String cambio(String entrada) {
		Matcher asd = Pattern.compile("([0-9]?.[0-9]+) (\\S+) a (\\S+)").matcher(entrada);
		String unidadLLegada = null;
		String unidadDestino = null;
		double valor = 0;
		if (asd.find()) {
			unidadLLegada = asd.group(2);
			unidadDestino = asd.group(3);
			valor = Double.parseDouble(asd.group(1));
		}		
		return convertir(unidadLLegada, unidadDestino, valor);
	}
	
	
	public String convertir(String origen, String destino, double valor)
	{
		double de=0, conv=1;
		cargarSistemas();
		
		char tipo = verificarUnidades(origen, destino);
		if(tipo=='N')
			return "No se puede convertir de " + origen + " a " + destino;
		
		switch(tipo)
		{
			case 'm':
			{
				char flag=this.masaSI.containsKey(origen)?'I':'A';
				char f=this.masaSA.containsKey(destino)?'A':'I';
				
				conv= flag=='I'? this.masaSI.get(origen):this.masaSA.get(origen);
				de=valor/conv;
				
				if(flag=='I' && f=='A' || flag=='A' && f=='I')
					conv=this.conversion.get(destino);
				else
					conv= f=='I'? this.masaSI.get(destino):this.masaSA.get(destino);
					
				break;
			}
			
			case 'l':
			{
				char flag=this.longitudSI.containsKey(origen)?'I':'A';
				char f=this.longitudSA.containsKey(destino)?'A':'I';
				
				conv= flag=='I'? this.longitudSI.get(origen):this.longitudSA.get(origen);
				de=valor/conv;
				
				if(flag=='I' && f=='A' || flag=='A' && f=='I')
					conv=this.conversion.get(destino);
				else
					conv= f=='I'? this.longitudSI.get(destino):this.longitudSA.get(destino);
					
				break;
			}
			
			case 'c':
			{
				char flag=this.capacidadSI.containsKey(origen)?'I':'A';
				char f=this.capacidadSA.containsKey(destino)?'A':'I';
				
				conv= flag=='I'? this.capacidadSI.get(origen):this.capacidadSA.get(origen);
				de=valor/conv;
				
				if(flag=='I' && f=='A' || flag=='A' && f=='I')
					conv=this.conversion.get(destino);
				else
					conv= f=='I'? this.capacidadSI.get(destino):this.capacidadSA.get(destino);
					
				break;
			}
			
			case 't':
			{				
				conv= this.tiempo.get(origen);
				de=valor/conv;
				conv=this.conversion.get(destino);
				break;
			}
		}
		
		return valor + " " + origen + " es equivalente a " + (Math.round(de*conv *100.00)/100.00) + " " + destino;
	}
	
	
	public char verificarUnidades(String origen, String destino)
	{
		char m='N';
		
		if((this.masaSA.containsKey(origen) || this.masaSI.containsKey(origen)) &&
				(this.masaSA.containsKey(destino) || this.masaSI.containsKey(destino)))
		{ m='m'; }
		else if((this.longitudSA.containsKey(origen) || this.longitudSI.containsKey(origen)) &&
				this.longitudSI.containsKey(destino) || this.longitudSA.containsKey(destino))
		{m='l';}
		else if((this.capacidadSA.containsKey(origen) || this.capacidadSI.containsKey(origen)) &&
				(this.capacidadSI.containsKey(destino) || this.capacidadSA.containsKey(destino)))
		{m='c';}
		else if(this.tiempo.containsKey(origen) && this.tiempo.containsKey(destino))
		{m='t';}
		
		return m;
	}
	
	
	public void cargarSistemas()
	{
		Scanner sc=null;
		try
		{
			sc = new Scanner(new File("Respuestas\\Unidades\\masaSA.dat"));
			while(sc.hasNextLine())
			{
				String linea = sc.nextLine();
				this.masaSA.put(linea.substring(0,  linea.indexOf('|')), Double.parseDouble(linea.substring(linea.indexOf('|')+1)));
			}
			
			sc = new Scanner(new File("Respuestas\\Unidades\\masaSI.dat"));
			while(sc.hasNextLine())
			{
				String linea = sc.nextLine();
				this.masaSI.put(linea.substring(0,  linea.indexOf('|')), Double.parseDouble(linea.substring(linea.indexOf('|')+1)));
			}
			
			sc=new Scanner(new File("Respuestas\\Unidades\\longitudSI.dat"));
			while(sc.hasNextLine())
			{
				String linea = sc.nextLine();
				this.longitudSI.put(linea.substring(0,  linea.indexOf('|')), Double.parseDouble(linea.substring(linea.indexOf('|')+1)));
			}
			
			sc=new Scanner(new File("Respuestas\\Unidades\\longitudSA.dat"));
			while(sc.hasNextLine())
			{
				String linea = sc.nextLine();
				this.longitudSA.put(linea.substring(0,  linea.indexOf('|')), Double.parseDouble(linea.substring(linea.indexOf('|')+1)));
			}
			
			sc=new Scanner(new File("Respuestas\\Unidades\\capacidadSI.dat"));
			while(sc.hasNextLine())
			{
				String linea = sc.nextLine();
				this.capacidadSI.put(linea.substring(0,  linea.indexOf('|')), Double.parseDouble(linea.substring(linea.indexOf('|')+1)));
			}
			
			sc=new Scanner(new File("Respuestas\\Unidades\\capacidadSA.dat"));
			while(sc.hasNextLine())
			{
				String linea = sc.nextLine();
				this.capacidadSA.put(linea.substring(0,  linea.indexOf('|')), Double.parseDouble(linea.substring(linea.indexOf('|')+1)));
			}
			
			sc=new Scanner(new File("Respuestas\\Unidades\\tiempo.dat"));
			while(sc.hasNextLine())
			{
				String linea = sc.nextLine();
				this.tiempo.put(linea.substring(0,  linea.indexOf('|')), Double.parseDouble(linea.substring(linea.indexOf('|')+1)));
			}
			
			sc=new Scanner(new File("Respuestas\\Unidades\\conversion.dat"));
			while(sc.hasNextLine())
			{
				String linea = sc.nextLine();
				this.conversion.put(linea.substring(0,  linea.indexOf('|')), Double.parseDouble(linea.substring(linea.indexOf('|')+1)));
			}
			
			sc=new Scanner(new File("Respuestas\\Unidades\\abreviacion.dat"));
			while(sc.hasNextLine())
			{
				String linea = sc.nextLine();
				this.abreviacion.add(linea);
			}
		}
		catch (Exception e)
		{
			e.getMessage();
		}
		finally
		{
			sc.close();
		}
	}
}

