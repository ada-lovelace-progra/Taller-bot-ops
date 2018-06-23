package resolvedores;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class Memes {
	
	private int id;
	private String key;
	private String path;

	public Memes()
	{}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getKey()
	{
		return this.key;
	}
	
	public String getPath()
	{
		return this.path;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public void setKey(String key)
	{
		this.key=key;
	}
	
	public void setPath(String path)
	{
		this.path=path;
	}
	
	
	@SuppressWarnings("finally")
	public String obtenerMeme(String meme)
	{
		Configuration conf = new Configuration();
		conf.configure("hibernate/hibernate.cfg.xml");
		SessionFactory factory = conf.buildSessionFactory();
		Session session = factory.openSession();
		String path = null;
		
		try
		{
			@SuppressWarnings("deprecation")
			Criteria cb = session.createCriteria(Memes.class).
			add(Restrictions.eq("key", meme));
			if(!cb.list().isEmpty())
			{
				path = ((Memes)cb.uniqueResult()).path;
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
			return path;
		}
	}
}