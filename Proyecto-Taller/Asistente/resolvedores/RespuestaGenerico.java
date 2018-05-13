package resolvedores;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class RespuestaGenerico implements RespuestaInterface {
	protected RespuestaInterface Siguiente;
	private ArrayList<String> peticiones = null, respuestas = null;
	protected String nombreDeLaClase;
	protected static int cordialidad;
	private final static double tope_cordialidad = 100;

	public RespuestaGenerico() {
		Siguiente = new Default();
	}

	public RespuestaGenerico(boolean op) {
	}

	public void siguiente(RespuestaInterface sig) {
		this.Siguiente = sig;
	}

	protected boolean consulta(String mensaje) {
		if (peticiones == null)
			cargarLista(nombreDeLaClase);
		int SubIndice = 0;
		for (String temp : peticiones) {
			if (mensaje.matches(".*" + temp + ".*")) {
				setear_Cordialidad(SubIndice, peticiones.size());
				return true;
			}
		}
		return false;
	}

	protected String respuesta() {
		String select = "respuestas_" + nombreDeLaClase;
		if (respuestas == null)
			cargarLista(select);
		if (respuestas == null)
			return null;
		return respuestas.get(subindice(respuestas));
	}

	private int subindice(ArrayList<String> temp) {
		double porcentajeDeCordialidad = RespuestaGenerico.cordialidad / RespuestaGenerico.tope_cordialidad;
		int tamArray = temp.size() - 1;
		int desface = (int) ((Math.random() * 10) % 3) - 1;
		int i = (int) (porcentajeDeCordialidad * tamArray) + desface;
		return i > -1 ? i < tamArray ? i : tamArray : 0;
	}

	private void cargarLista(String select) {
		Scanner entrada = null;
		try {
			ArrayList<String> temp;
			if (select.contains("respuesta"))
				temp = respuestas = new ArrayList<String>();
			else
				temp = peticiones = new ArrayList<String>();
			
			entrada = new Scanner(
					new File((select.contains("respuesta") ? "Respuestas\\" : "Peticiones\\\\") + select + ".dat"));

			while (entrada.hasNextLine()) {
				String nextLine = entrada.nextLine();
				if (nextLine.startsWith("\""))
					while (entrada.hasNextLine() && nextLine.endsWith("\""))
						nextLine += "\n" + entrada.nextLine();
				temp.add(nextLine);
			}
			entrada.close();
		} catch (Exception e) {
			if (entrada != null)
				entrada.close();
			System.out.println("no se encontro el arhivo " + select);
		}
	}

	private void setear_Cordialidad(double SubIndice, double TamArray) {
		if (TamArray == 1)
			return;
		TamArray--;
		int cordialidadTemp;
		int CordialidadEnviada = (int) ((SubIndice / TamArray) * tope_cordialidad);
		if (cordialidad < 0)
			cordialidadTemp = CordialidadEnviada;
		else
			cordialidadTemp = ((CordialidadEnviada - cordialidad) / 3) + cordialidad;

		if (cordialidadTemp > tope_cordialidad)
			cordialidadTemp = (int) (tope_cordialidad - 1);

		cordialidad = cordialidadTemp;
	}

}
