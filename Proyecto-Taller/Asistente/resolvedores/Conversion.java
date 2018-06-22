package resolvedores;

import java.util.Hashtable;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class Conversion {
	
private Hashtable<String, String> bases;
	
	private String de;
	private String a;
	private double valor;
	private int id;
	
	public Conversion()
	{
		setearBases();
	}
	
	public Conversion(int id, String de, String a, double valor)
	{
		this.id=id;
		this.de=de;
		this.a=a;
		this.valor=valor;
		setearBases();
	}
	
	public void setearBases()
	{
		this.bases = new Hashtable<String, String>();
		bases.put("Masa", "kg");
		bases.put("Capacidad", "l");
		bases.put("Longitud", "m");
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getDe()
	{
		return this.de;
	}
	
	public String getA()
	{
		return this.a;
	}
	
	public double getValor()
	{
		return this.valor;
	}
	
	public void setDe(String de)
	{
		this.de=de;
	}
	
	public void setA(String a)
	{
		this.a=a;
	}
	
	public void setValor(double valor)
	{
		this.valor=valor;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	
	@SuppressWarnings({ "finally", "deprecation" })
	public Conversion getConversion(UnidadesSM de, UnidadesSM a)
	{
		Configuration conf = new Configuration();
		conf.configure("hibernate/hibernate.cfg.xml");
		SessionFactory factory = conf.buildSessionFactory();
		Session session = factory.openSession();
		Conversion c= null;
		Conversion temp = null; //temp lo uso en el caso de que tenga que pasar a alguna base
		
		try
		{
			Criteria crit = session.createCriteria(this.getClass()).add(Restrictions.or(
					Restrictions.and(Restrictions.eq("de", de.getAbreviacion()), Restrictions.eqOrIsNull("a", a.getAbreviacion())),
					Restrictions.and(Restrictions.eq("de", a.getAbreviacion()), Restrictions.eqOrIsNull("a", de.getAbreviacion()))));
			
			c = (Conversion)crit.uniqueResult(); //ver si de aca salta al catch en lugar de preguntar si es nulo
			if(c!=null)
			{
				if(c.getDe().equals(a.getAbreviacion()))
					c.setValor(1/c.valor);
			}
			else
			{			
				//Si no los encuentra, tiene que ir a la base que le corresponda
				String base = bases.get(de.getTipo());
				
				try
				{
					//de "De" a "Base", o de "Base" a "De"
					
					crit = session.createCriteria(this.getClass()).add(Restrictions.or(
						Restrictions.and(Restrictions.eq("de", de.getAbreviacion()), Restrictions.eqOrIsNull("a", base)),
						Restrictions.and(Restrictions.eq("de", base), Restrictions.eqOrIsNull("a", de.getAbreviacion()))));
				
					temp = (Conversion)crit.uniqueResult();
					if(temp.getDe().equals(base))
						temp.setValor(1/temp.valor);
					
					//De "base" a "a"
					
					crit = session.createCriteria(this.getClass()).add(Restrictions.or(
							Restrictions.and(Restrictions.eq("de", a.getAbreviacion()), Restrictions.eqOrIsNull("a", base)),
							Restrictions.and(Restrictions.eq("de", base), Restrictions.eqOrIsNull("a", a.getAbreviacion()))));
					
					c = (Conversion)crit.uniqueResult();
					if(c.getDe().equals(base))
						c.setValor(1/c.valor);	
				}
				catch(Exception e)
				{
					//Si viene aca, es porque tiene que pasar desde "A" a "base"
					
					crit = session.createCriteria(this.getClass()).add(Restrictions.or(
							Restrictions.and(Restrictions.eq("de", a.getAbreviacion()), Restrictions.eqOrIsNull("a", base)),
							Restrictions.and(Restrictions.eq("de", base), Restrictions.eqOrIsNull("a", a.getAbreviacion()))));
					
						temp = (Conversion)crit.uniqueResult();
						if(temp.getDe().equals(base))
							temp.setValor(1/temp.valor);
						
						crit = session.createCriteria(this.getClass()).add(Restrictions.or(
							Restrictions.and(Restrictions.eq("de", de.getAbreviacion()), Restrictions.eqOrIsNull("a", base)),
							Restrictions.and(Restrictions.eq("de", base), Restrictions.eqOrIsNull("a", de.getAbreviacion()))));
						
					c = (Conversion)crit.uniqueResult();
					if(c.getDe().equals(base))
						c.setValor(1/c.valor);					
				}
				finally
				{
					if(c!=null)
					{c.setDe(de.getAbreviacion());
					c.setA(a.getAbreviacion());
					c.valor = c.valor * temp.valor;}
				}
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
			return c;
		}
	}
}
