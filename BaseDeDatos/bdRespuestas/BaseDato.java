package bdRespuestas;

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
		if (session == null) {
			Configuration conf = new Configuration();
			conf.configure("bdRespuestas/hibernate.cfg.xml");
			factory = conf.buildSessionFactory();
			session = factory.openSession();
		}
	}

	@SuppressWarnings("all")
	public ArrayList<RespuestaBD> traerDatos(String clase) {
		try {
			ArrayList<RespuestaBD> peticiones = new ArrayList<>();
			Criteria cb = session.createCriteria(RespuestaBD.class).add(Restrictions.eq("clase", clase));
			if (cb != null && cb.list() != null && !cb.list().isEmpty()) {
				peticiones.addAll((ArrayList<RespuestaBD>) cb.list());
				return peticiones;
			}
		} catch (Exception e) {
		}
		return null;
	}

}
