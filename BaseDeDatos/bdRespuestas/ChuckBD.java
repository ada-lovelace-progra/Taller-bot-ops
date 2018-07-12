package bdRespuestas;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

public class ChuckBD extends BaseDato {
	private int id;
	private String fact;
	private Date fechaLectura;

	public ChuckBD() {
	}

	public ChuckBD(int id, String fact, Date fechaLectura) {
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

		Transaction tx = session.getTransaction();
		if (tx != null)
			tx = session.beginTransaction();
		ChuckBD fact = null;

		try {
			@SuppressWarnings("deprecation")
			Criteria cb = session.createCriteria(ChuckBD.class);
			cb.add(Restrictions.isNull("fechaLectura"));
			cb.add(Restrictions.sqlRestriction("1=1 order by random()"));
			cb.setMaxResults(1);
			if (!cb.list().isEmpty()) {
				fact = (ChuckBD) cb.uniqueResult();
				fact.setFechaLectura(DateTime.now().toDate());
				session.saveOrUpdate(fact);
				tx.commit();
			}
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return null;
		}
		return fact.fact;
	}

}
