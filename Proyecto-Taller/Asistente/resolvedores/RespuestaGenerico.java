package resolvedores;

import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/** 
 * Todas las respuestas de ada deberan extender de esta clase, es el "Handler" del asistente.
 */
public abstract class RespuestaGenerico {
	
	private int id;
	private String clase;
	private String peticion;
	private String respuesta;

	protected RespuestaGenerico Siguiente = null;
	private ArrayList<RespuestaGenerico> peticiones = null, respuestas = null;
	protected static double cordialidad;
	public static String nombre;
	
	public int getId()
	{
		return this.id;
	}
	
	public String getClase()
	{
		return this.clase;
	}
	
	public String getPeticion()
	{
		return this.peticion;
	}
	
	public String getRespuesta()
	{
		return this.respuesta;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public void setClase(String clase)
	{
		this.clase=clase;
	}
	
	public void setPeticion(String peticion)
	{
		this.peticion=peticion;
	}
	
	public void setRespuesta(String res)
	{
		this.respuesta=res;
	}

	public RespuestaGenerico() {
		Siguiente = new Default();
	}

	public RespuestaGenerico(boolean op) {
	}

	public void siguiente(RespuestaGenerico sig) {
		this.Siguiente = sig;
	}

	public String intentar(String mensaje) {
		String temp = intentarResponder(mensaje);
		if (temp != null)
			return temp;
		else if (Siguiente != null) // esto va a ser null cuando es la clase default... la cual es la ultima
			return Siguiente.intentar(mensaje);
		else
			return null;
	}

	public abstract String intentarResponder(String mensaje);

	protected boolean consulta(String mensaje) {
		if (peticiones == null)
			cargarLista(this.getClass().getSimpleName());
		String nombreLower = nombre.toLowerCase();
		int index=-1;
		for (RespuestaGenerico temp : peticiones) {
			String corregido = mensaje.replaceAll("[^a-z_0-9_ ]", "");
			index++;
			if (corregido.matches(".*" + temp.peticion + ".*") || temp.peticion.contains(corregido)) { //ver de guardar regex en la db
				this.respuesta=this.peticiones.get(index).respuesta;
				return true;
			}
		}
		return false;
	}

	protected String respuesta() 
	{
		 if(this.respuesta!=null)
			 return this.respuesta;
		
		 return null;		 
	}

	@SuppressWarnings("unchecked")
	private void cargarLista(String clase) {
		
		Configuration conf = new Configuration();
		conf.configure("hibernate/hibernate.cfg.xml");
		SessionFactory factory = conf.buildSessionFactory();
		Session session = factory.openSession();
		this.peticiones = new ArrayList<>();
		
		try
		{
			@SuppressWarnings("deprecation")
			Criteria cb = session.createCriteria(this.getClass()).add(Restrictions.eq("clase", clase));
			//mensaje = mensaje.replaceAll("[^a-z_0-9_ ]", "");
			if(!cb.list().isEmpty())
			{
				this.peticiones.addAll((ArrayList<RespuestaGenerico>)cb.list());
			}
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			//return false;
		}
		finally
		{
			session.close();
			factory.close();
			//return !this.peticiones.isEmpty();
		}
	}


//
//	private int subindice(ArrayList<String> temp) {
//		int tamArray = temp.size() - 1;
//		int desface = (int) (Math.random() * 5) - 2;
//		int i = (int) (cordialidad * tamArray) + desface;
//		return i > -1 ? i < tamArray ? i : tamArray : 0;
//	}

	
//	private void setear_Cordialidad(double SubIndice, double TamArray) {
//		if (TamArray == 1)
//			return;
//		double cordialidadTemp;
//		double CordialidadEnviada = SubIndice / TamArray;
//		if (cordialidad < 0)
//			cordialidadTemp = CordialidadEnviada;
//		else
//			cordialidadTemp = ((CordialidadEnviada - cordialidad) / 3) + cordialidad;
//
//		if (cordialidadTemp > 1)
//			cordialidadTemp = 0.99;
//
//		cordialidad = cordialidadTemp;
//	}

}
