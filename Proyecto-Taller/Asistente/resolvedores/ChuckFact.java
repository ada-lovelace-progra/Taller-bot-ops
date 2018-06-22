package resolvedores;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

public class ChuckFact {
	private int id;
	private String fact;
	private Date fechaLectura;

	public ChuckFact() {
	}

	public ChuckFact(int id, String fact, Date fechaLectura) {
		this.id = id;
		this.fact = fact;
		this.fechaLectura = fechaLectura;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFact(String fact) {
		this.fact = fact;
	}

	public void setFechaLectura(Date fecha) {
		this.fechaLectura = fecha;
	}

	public int getId() {
		return this.id;
	}

	public String getFact() {
		return this.fact;
	}

	public Date getFechaLectura() {
		return this.fechaLectura;
	}

	public String obtenerChuckFact() {
		Configuration conf = new Configuration();
		conf.configure("hibernate/hibernate.cfg.xml");
		SessionFactory factory = conf.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		ChuckFact fact = null;

		try {
			@SuppressWarnings("deprecation")
			Criteria cb = session.createCriteria(ChuckFact.class);
			cb.add(Restrictions.isNull("fechaLectura"));
			cb.add(Restrictions.sqlRestriction("1=1 order by random()"));
			cb.setMaxResults(1);
			if (!cb.list().isEmpty()) {
				fact = (ChuckFact) cb.uniqueResult();
				fact.setFechaLectura(DateTime.now().toDate());
				session.saveOrUpdate(fact);
				tx.commit();
			}
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			session.close();
			factory.close();
			return null;
		}
		session.close();
		factory.close();
		return fact.fact;
	}

}
