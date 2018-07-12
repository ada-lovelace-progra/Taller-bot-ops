package bdRespuestas;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DeudasBD extends BaseDato {

	private String prestamista;
	private String deudor;
	private float monto;
	private Transaction tx = null;
	private String id;

	private static SessionFactory factory;
	protected static Session session;

	public DeudasBD() {
		if (session == null) {
			Configuration conf = new Configuration();
			conf.configure("bdRespuestas/hibernateDeudas.cfg.xml");
			factory = conf.buildSessionFactory();
			session = factory.openSession();
		}
	}

	public DeudasBD(String prestamista, String deudor, float monto) {
		this.prestamista = prestamista;
		this.deudor = deudor;
		this.id = prestamista + deudor;
		this.monto = monto;
	}

	public void setPrestamista(String prestamista) {
		this.prestamista = prestamista;
	}

	public void setDeudor(String deudor) {
		this.deudor = deudor;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public String getPrestamista() {
		return this.prestamista;
	}

	public String getDeudor() {
		return this.deudor;
	}

	public float getMonto() {
		return this.monto;
	}

	public boolean acreditarDeuda(String prestamista, String deudor, float monto) // Para un solo caso: A le debe a B o
																					// al revés
	{
		// if(!buscarUsuario(prestamista) && ! buscarUsuario(deudor))
		// return false; //Falta alguno de los usuarios en la base de datos

		Float deuda = darDeBaja(prestamista, deudor);
		deuda -= darDeBaja(deudor, prestamista);
		deuda += monto;
		if (deuda > 0)
			return actualizarDeuda(prestamista, deudor, deuda);
		if (deuda < 0)
			return actualizarDeuda(deudor, prestamista, -deuda);
		return true;
	}

	public float buscarDeuda(String prestamista, String deudor) {
		try {
			@SuppressWarnings("deprecation")
			Criteria crit = session.createCriteria(DeudasBD.class).add(
					Restrictions.and(Restrictions.eq("prestamista", prestamista), Restrictions.eq("deudor", deudor)))
					.setProjection(Projections.property("monto"));

			if (!crit.list().isEmpty()) {
				return (float) crit.uniqueResult();
			}

		} catch (HibernateException e) {
			return -1;
		}
		return 0;
	}

	@SuppressWarnings("all")
	public ArrayList<DeudasBD> buscarDeuda(String user) {
		try {
			ArrayList<DeudasBD> peticiones = new ArrayList<>();
			Criteria crit = session.createCriteria(DeudasBD.class)
					.add(Restrictions.or(Restrictions.eq("prestamista", user), Restrictions.eq("deudor", user)));
			if (crit != null && crit.list() != null && !crit.list().isEmpty()) {
				peticiones.addAll((ArrayList<DeudasBD>) crit.list());
				return peticiones;
			}

		} catch (HibernateException e) {
			return null;
		}
		return null;
	}

	public float darDeBaja(String prestamista, String deudor) {
		try {
			if (tx == null)
				tx = session.beginTransaction();

			@SuppressWarnings("deprecation")
			Criteria crit = session.createCriteria(DeudasBD.class).add(
					Restrictions.and(Restrictions.eq("prestamista", prestamista), Restrictions.eq("deudor", deudor)))
					.setMaxResults(1);

			DeudasBD deuda = (DeudasBD) crit.uniqueResult();
			if (crit != null && crit.list() != null && !crit.list().isEmpty()) {
				deuda = (DeudasBD) crit.uniqueResult();
				session.delete(deuda);
				tx.commit();
				tx = null;
				return deuda.getMonto();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private boolean actualizarDeuda(String prestamista, String deudor, float monto) {

		if (tx == null)
			tx = session.beginTransaction();

		DeudasBD deuda = null;
		try {
			@SuppressWarnings("deprecation")
			Criteria crit = session.createCriteria(DeudasBD.class).add(
					Restrictions.and(Restrictions.eq("prestamista", prestamista), Restrictions.eq("deudor", deudor)))
					.setMaxResults(1);

			if (crit != null && crit.list() != null && !crit.list().isEmpty()) {
				deuda = (DeudasBD) crit.uniqueResult();
				deuda.setMonto(monto);
				session.saveOrUpdate(deuda);
				tx.commit();
				tx = null;
				return true;
			} else {
				session.saveOrUpdate(new DeudasBD(prestamista, deudor, monto));
				tx.commit();
				tx = null;
				return true;
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return false;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
