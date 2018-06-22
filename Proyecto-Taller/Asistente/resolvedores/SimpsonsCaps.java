package resolvedores;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class SimpsonsCaps {
	
	private int id;
	private String frase;
	
	public SimpsonsCaps()
	{}
	
	public SimpsonsCaps(int id, String frase)
	{
		this.id=id;
		this.frase=frase;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public void setFrase(String frase)
	{
		this.frase=frase;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public  String getFrase()
	{
		return this.frase;
	}
	
	
	@SuppressWarnings("finally")
	public String getSimpsonsCaps()
	{
		Configuration conf = new Configuration();
		conf.configure("hibernate/hibernate.cfg.xml");
		SessionFactory factory = conf.buildSessionFactory();
		Session session = factory.openSession();
		SimpsonsCaps cap = null;
		
		try
		{
			@SuppressWarnings("deprecation")
			Criteria cb = session.createCriteria(SimpsonsCaps.class).
			add(Restrictions.sqlRestriction("1=1 order by random()")).setMaxResults(1);
			if(!cb.list().isEmpty())
			{
				cap = (SimpsonsCaps) cb.uniqueResult();
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
			return cap!=null?cap.frase:null;
		}
	}

}
