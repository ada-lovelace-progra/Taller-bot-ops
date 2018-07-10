package dbUsuarios;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class BaseDato {

	private static SessionFactory factory;
	protected static Session session;

	public BaseDato() {
		if (session == null) {
			Configuration conf = new Configuration();
			conf.configure("dbUsuarios/hibernate.cfg.xml");
			factory = conf.buildSessionFactory();
			session = factory.openSession();
		}
	}

	@SuppressWarnings("all")
	public boolean traerDatos(String user, String pass) {
		try {
			Criteria cb = session.createCriteria(RespuestaBD.class).add(
					Restrictions.and(Restrictions.eq("usuario", user.toLowerCase()), Restrictions.eq("pass", pass)));

			if (cb != null && cb.list() != null && !cb.list().isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static boolean comprobarUser(String user) {
		try {
			@SuppressWarnings("deprecation")
			Criteria crit = session.createCriteria(RespuestaBD.class)
					.add(Restrictions.eq("usuario", user.toLowerCase()));
			if (crit != null && crit.list() != null && !crit.list().isEmpty())
				return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static boolean crearUsuario(String user, String pass) {
		Transaction tx = session.beginTransaction();
		try {
			RespuestaBD res = new RespuestaBD(5, user, pass);
			session.save(res);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return false;
		}
	}

}
