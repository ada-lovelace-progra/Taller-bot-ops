package bdRespuestas;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

public class UnidadesSMBD extends BaseDato {

	private String nombre;
	private String abreviacion;
	private String tipo;
	private String sistema;

	public UnidadesSMBD() {
	}

	public UnidadesSMBD(String nombre, String abreviacion, String tipo, String sistema) {
		this.nombre = nombre;
		this.abreviacion = abreviacion;
		this.sistema = sistema;
		this.tipo = tipo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setAbreviacion(String abreviacion) {
		this.abreviacion = abreviacion;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getAbreviacion() {
		return this.abreviacion;
	}

	public String getTipo() {
		return this.tipo;
	}

	public String getSistema() {
		return this.sistema;
	}

	public String convertir(String de, String a, double valor) {
		de = limpiarCaracteres(de);
		a = limpiarCaracteres(a);
		UnidadesSMBD ude = new UnidadesSMBD();
		UnidadesSMBD ua = new UnidadesSMBD();
		ConversionBD c = new ConversionBD();
		ude = ude.buscarUnidad(de);
		ua = ua.buscarUnidad(a);

		if (ude == null || ua == null)
			return "Faltan unidades en la base de datos";

		if (!verificarUnidades(ude, ua))
			return "No se pueden convertir unidades de " + de.toString() + " a " + a.toString() + ".";

		c = c.getConversion(ude, ua);
		if (c == null)
			return "No se encuentran las unidades " + ude.toString() + " y " + ua.toString() + " para la conversiï¿½n.";

		double res = valor * c.getValor();

		return valor + " " + ude.toString() + " es equivalente a " + (Math.round(res * 100.00) / 100.00) + " "
				+ ua.toString();
	}

	public boolean verificarUnidades(UnidadesSMBD de, UnidadesSMBD a) {
		if (de.tipo.equals(a.tipo))
			return true;
		return false;
	}

	public UnidadesSMBD buscarUnidad(String param) {
		UnidadesSMBD unidad = null;

		try {
			@SuppressWarnings("deprecation")
			Criteria cb = session.createCriteria(UnidadesSMBD.class)
					.add(Restrictions.or(Restrictions.ilike("abreviacion", param), Restrictions.ilike("nombre", param)))
					.setMaxResults(1);
			if (!cb.list().isEmpty()) {
				unidad = (UnidadesSMBD) cb.uniqueResult();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
		return unidad;
	}

	public String limpiarCaracteres(String linea) {
		char[] array = { 'á', 'é', 'í', 'ó', 'ú', 's' };
		for (char c : array) {
			if (!linea.equals("s") && !linea.startsWith("s") || (linea.length() > 2 && linea.endsWith("s")))
				linea = linea.toLowerCase().replace(c, '%');
		}
		return linea;
	}

	public String toString() {
		return this.nombre + " (" + this.abreviacion + ")";
	}
}
