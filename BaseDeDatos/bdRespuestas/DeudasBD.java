package bdRespuestas;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DeudasBD extends BaseDato{
	
	private String prestamista;
	private String deudor;
	private double monto;
	
	public DeudasBD(String prestamista, String deudor, double monto)
	{
		this.prestamista = prestamista;
		this.deudor=deudor;
		this.monto=monto;
	}
	
	public DeudasBD()
	{}
	
	public void setPrestamista(String prestamista)
	{
		this.prestamista=prestamista;
	}
	
	public void setDeudor(String deudor)
	{
		this.deudor=deudor;
	}
	
	public void setMonto(double monto)
	{
		this.monto=monto;
	}
	
	public String getPrestamista()
	{
		return this.prestamista;
	}
	
	public String getDeudor()
	{
		return this.deudor;
	}
	
	public double getMonto()
	{
		return this.monto;
	}
	
	
	public boolean acreditarDeuda(String prestamista, String deudor, double monto) //Para un solo caso: A le debe a B o al revés
	{
//		if(!buscarUsuario(prestamista) && ! buscarUsuario(deudor))
//			return false; //Falta alguno de los usuarios en la base de datos
		
		double deuda = buscarDeuda(prestamista, deudor);
		if(deuda==-1) //Algo falló en la consulta
			return false;
		deuda +=monto;
		
		return actualizarDeuda(prestamista, deudor, deuda);
	}
	
	
	public double buscarDeuda(String prestamista, String deudor)
	{
		try
		{
			@SuppressWarnings("deprecation")
			Criteria crit = session.createCriteria(DeudasBD.class).add(Restrictions.and(
					Restrictions.eq("prestamista", prestamista), Restrictions.eq("deudor", deudor)))
					.setProjection(Projections.property("monto"));
			
			if(!crit.list().isEmpty())
			{
				return (double)crit.uniqueResult();
			}
			
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			return -1;
		}
		return -1;
	}
	
	public boolean actualizarDeuda(String prestamista, String deudor, double monto)
	{
		Transaction tx = session.beginTransaction();
		DeudasBD deuda = null;
		try
		{
			@SuppressWarnings("deprecation")
			Criteria crit = session.createCriteria(DeudasBD.class).add(Restrictions.and(
					Restrictions.eq("prestamista", prestamista), Restrictions.eq("deudor", deudor))).setMaxResults(1);
			
			if(!crit.list().isEmpty())
			{
				deuda = (DeudasBD)crit.uniqueResult();
				deuda.setMonto(monto);
				session.saveOrUpdate(deuda);
				tx.commit();
				return true;
			}
		}
		catch(HibernateException e)
		{
			if(tx != null)
				tx.rollback();
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
