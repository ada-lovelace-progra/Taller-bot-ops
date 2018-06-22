package resolvedores;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.joda.time.DateTime;

public class Evento {
	private int id;
	private String descripcion;
	private Date fecha;

	public Evento() {
	}
	
	public Evento(int id, Date fecha, String descripcion)
	{
		this.id=id;
		this.descripcion=descripcion;
		this.fecha=fecha;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public void setFecha(Date fecha)
	{
		this.fecha=fecha;
	}
	
	public void setDescripcion(String descripcion)
	{
		this.descripcion=descripcion;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getDescripcion()
	{
		return this.descripcion;
	}
	
	public Date getFecha()
	{
		return this.fecha;
	}
	
	
	@SuppressWarnings({ "deprecation", "finally" })
	public Evento proximoEvento()
	{
		Configuration conf = new Configuration();
		conf.configure("hibernate/hibernate.cfg.xml");
		SessionFactory factory = conf.buildSessionFactory();
		Session session = factory.openSession();
		Evento evento = null;
		try
		{
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Evento> query = cb.createQuery(Evento.class);
			Root<Evento> root = query.from(Evento.class);
			
			query.select(root).where(cb.greaterThanOrEqualTo(root.get("fecha"),
					DateTime.now().toDateMidnight().toDate())).orderBy();
			evento=session.createQuery(query).getSingleResult();
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
			return evento;
		}
	}
	
	
	public boolean crearEvento(Evento evento)
	{
		Configuration conf = new Configuration();
		conf.configure("hibernate/hibernate.cfg.xml");
		
		SessionFactory factory = conf.buildSessionFactory();
		Session session = factory.openSession();		
		Transaction tx = session.beginTransaction();
		
		try
		{
			session.saveOrUpdate(evento);
			tx.commit();
			
			return true;
		}
		catch (HibernateException e)
		{
			if(tx != null)
				tx.rollback();
			e.printStackTrace();
			
			return false;
		}
		finally
		{
			session.close();
			factory.close();
		}

	}
	
	public String toString()
	{
		return new SimpleDateFormat("dd/MM/yyyy").format(this.fecha) + this.descripcion;
	}
}
