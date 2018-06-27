package bdResponderGenerico;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.joda.time.DateTime;

public class EventoBD extends BaseDato {
	private int id;
	private String descripcion;
	private Date fecha;

	public EventoBD() {
	}

	public EventoBD(int id, Date fecha, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
		this.fecha = fecha;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getId() {
		return this.id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	@SuppressWarnings("all")
	public EventoBD proximoEvento() {
		EventoBD evento = null;
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<EventoBD> query = cb.createQuery(EventoBD.class);
			Root<EventoBD> root = query.from(EventoBD.class);

			query.select(root)
					.where(cb.greaterThanOrEqualTo(root.get("fecha"), DateTime.now().toDateMidnight().toDate()))
					.orderBy();
			
			evento = session.createQuery(query).getSingleResult();
		} catch (Exception e) {
			return null;
		}
		return evento;
	}

	public boolean crearEvento(EventoBD evento) {
		Transaction tx = session.beginTransaction();

		try {
			id=1;
			session.saveOrUpdate(evento);
			tx.commit();

			return true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

			return false;
		}
	}

	public String toString() {
		return new SimpleDateFormat("dd/MM/yyyy").format(this.fecha) + this.descripcion;
	}
}
