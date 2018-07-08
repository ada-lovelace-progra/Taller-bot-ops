package dbUsuarios;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	public boolean traerDatos(String user) {
		try {
			Criteria cb = session.createCriteria(RespuestaBD.class).add(Restrictions.eq("Usuario_Pass", user.toLowerCase()));
			// mensaje = mensaje.replaceAll("[^a-z_0-9_ ]", "");
			if (cb != null && cb.list() != null && !cb.list().isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
