package bdRespuestas;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

public class MemesBD extends BaseDato {

	private int id;
	private String key;
	private String path;

	public MemesBD() {
	}

	public int getId() {
		return this.id;
	}

	public String getKey() {
		return this.key;
	}

	public String getPath() {
		return this.path;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String obtenerMeme(String meme) {
		String path = null;

		try {
			@SuppressWarnings("deprecation")
			Criteria cb = session.createCriteria(MemesBD.class).add(Restrictions.eq("key", meme));
			if (!cb.list().isEmpty()) {
				path = ((MemesBD) cb.uniqueResult()).path;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
		return path;
	}
}