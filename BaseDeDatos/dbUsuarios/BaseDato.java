package dbUsuarios;

import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class BaseDato {

	private static SessionFactory factory;
	protected static Session session;

	public BaseDato() {
		new Thread() {
			public void run() {
				if (session == null) {
					Configuration conf = new Configuration();
					conf.configure("dbUsuarios/hibernate.cfg.xml");
					factory = conf.buildSessionFactory();
					session = factory.openSession();
				}
			}
		}.start();
	}

	@SuppressWarnings("all")
	public boolean traerDatos(String user) {
		try {
			ArrayList<RespuestaBD> peticiones = new ArrayList<>();
			Criteria cb = session.createCriteria(RespuestaBD.class).add(Restrictions.eq("Usuario", user));
			// mensaje = mensaje.replaceAll("[^a-z_0-9_ ]", "");
			if (cb != null && cb.list() != null && !cb.list().isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("\n\n\nerror en criteria con la clase: " + user + "\n\n\n");
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
