package bdRespuestas;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class EventosBD {
	private String descripcion;
	private String fecha;
	private Transaction tx;
	private String usuario;

	private static SessionFactory factory;
	protected static Session session;

	public EventosBD() {
		if (session == null) {
			Configuration conf = new Configuration();
			conf.configure("bdRespuestas/hibernateEventos.cfg.xml");
			factory = conf.buildSessionFactory();
			session = factory.openSession();
		}
	}

	public EventosBD(String fecha, String descripcion) {
		this.descripcion = descripcion;
		this.fecha = fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public String getFecha() {
		return this.fecha;
	}

	@SuppressWarnings("all")
	public EventosBD proximoEvento(String usuario) {
		// pendiente agregar que filtre por usuario (no tengo ganas ahora...)
		EventosBD evento = null;
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<EventosBD> query = cb.createQuery(EventosBD.class);
			Root<EventosBD> root = query.from(EventosBD.class);

			Criteria c = session.createCriteria(EventosBD.class).add(Restrictions.eq("usuario", usuario.toLowerCase()));
			List<EventosBD> lista = c.list();
			lista.sort(new Comparator<EventosBD>() {
				public int compare(EventosBD o1, EventosBD o2) {
					return o1.fecha.compareTo(o2.fecha);
				}
			});
			return lista.get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean crearEvento(EventosBD evento) {
		if (tx == null)
			tx = session.beginTransaction();

		try {
			session.saveOrUpdate(evento);
			tx.commit();
			tx = null;
			return true;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			return false;
		}
	}

	public String darDeBaja(String fecha) {
		try {
			if (tx == null)
				tx = session.beginTransaction();

			@SuppressWarnings("deprecation")
			Criteria crit = session.createCriteria(EventosBD.class)
					.add(Restrictions.and(Restrictions.eq("fecha", fecha))).setMaxResults(1);

			EventosBD evento = (EventosBD) crit.uniqueResult();
			if (crit != null && crit.list() != null && !crit.list().isEmpty()) {
				evento = (EventosBD) crit.uniqueResult();
				session.delete(evento);
				tx.commit();
				tx = null;
				return evento.fecha;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toString() {
		return new SimpleDateFormat("dd/MM/yyyy").format(this.fecha) + this.descripcion;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getUsuario() {
		return this.usuario;
	}
}
