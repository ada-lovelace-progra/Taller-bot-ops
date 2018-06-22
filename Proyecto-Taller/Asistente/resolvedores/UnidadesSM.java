package resolvedores;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class UnidadesSM {
	
	private String nombre;
	private String abreviacion;
	private String tipo;
	private String sistema;
	
	public UnidadesSM()
	{}

	public UnidadesSM(String nombre, String abreviacion, String tipo, String sistema)
	{
		this.nombre=nombre;
		this.abreviacion=abreviacion;
		this.sistema=sistema;
		this.tipo=tipo;
	}
	
	public void setNombre(String nombre)
	{
		this.nombre=nombre;
	}
	
	public void setAbreviacion(String abreviacion)
	{
		this.abreviacion=abreviacion;
	}
	
	public void setTipo(String tipo)
	{
		this.tipo=tipo;
	}
	
	public void setSistema(String sistema)
	{
		this.sistema=sistema;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public String getAbreviacion()
	{
		return this.abreviacion;
	}
	
	public String getTipo()
	{
		return this.tipo;
	}
	
	public String getSistema()
	{
		return this.sistema;
	}


	public String convertir(String de, String a, double valor)
	{
		de = limpiarCaracteres(de);
		a = limpiarCaracteres(a);
		UnidadesSM ude = new UnidadesSM();
		UnidadesSM ua = new UnidadesSM();
		Conversion c = new Conversion();
		ude=ude.buscarUnidad(de);
		ua=ua.buscarUnidad(a);
		
		if(ude==null || ua == null)
			return "Faltan unidades en la base de datos";
		
		if(!verificarUnidades(ude, ua))
			return "No se pueden convertir unidades de " + de.toString() + " a " + a.toString() + ".";
		
		c= c.getConversion(ude, ua);
		if(c==null)
			return "No se encuentran las unidades " + ude.toString() + " y " + ua.toString() + " para la conversión.";
		
		double res = valor * c.getValor();
		
		return valor + " " + ude.toString() + " es equivalente a " + (Math.round(res*100.00)/100.00) + " " + ua.toString();
	}
	
	public boolean verificarUnidades(UnidadesSM de, UnidadesSM a)
	{
		if(de.tipo.equals(a.tipo))
			return true;
		return false;
	}
	
	@SuppressWarnings("finally")
	public UnidadesSM buscarUnidad(String param)
	{
		//param = limpiarCaracteres(param);
		Configuration conf = new Configuration();
		conf.configure("hibernate/hibernate.cfg.xml");
		SessionFactory factory = conf.buildSessionFactory();
		Session session = factory.openSession();
		UnidadesSM unidad=null;
		
		try
		{
			@SuppressWarnings("deprecation")
			Criteria cb = session.createCriteria(UnidadesSM.class).
			add(Restrictions.or(Restrictions.ilike("abreviacion", param), Restrictions.ilike("nombre", param))).setMaxResults(1);
			if(!cb.list().isEmpty())
			{
				unidad = (UnidadesSM) cb.uniqueResult();
			}			
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			session.close();
			factory.close();
			return unidad;
		}
	}
	
	public String limpiarCaracteres(String linea)
	{
		char[] array = {'á', 'é', 'í', 'ó', 'ú', 's'};

		for(char c : array)
		{
			if(!linea.equals("s") && !linea.startsWith("s") || (linea.length() > 2 && linea.endsWith("s")))
				linea=linea.toLowerCase().replace(c, '%');
		}
		return linea;
	}
		
	public String toString()
	{
		return this.nombre + " (" + this.abreviacion + ")";
	}
}
