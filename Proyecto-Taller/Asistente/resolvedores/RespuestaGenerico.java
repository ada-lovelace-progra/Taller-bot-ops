package resolvedores;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/** 
 * Todas las respuestas de ada deberan extender de esta clase, es el "Handler" del asistente.
 */
public abstract class RespuestaGenerico {

	protected RespuestaGenerico Siguiente = null;
	private ArrayList<String> peticiones = null, respuestas = null;
	protected static double cordialidad;
	public static String nombre;

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
		if (temp != null) {
			// String siguitentePosibilidad = Siguiente.intentar(mensaje);
			// return temp + (siguitentePosibilidad == null ? "" : siguitentePosibilidad);
			return temp;
		} else if (Siguiente != null) // esto va a ser null cuando es la clase default... la cual es la ultima
			return Siguiente.intentar(mensaje);
		else
			return null;
	}

	public abstract String intentarResponder(String mensaje);

	protected boolean consulta(String mensaje) {
		if (peticiones == null)
			cargarLista(this.getClass().getSimpleName());
		int SubIndice = 0;
		String nombreLower = nombre.toLowerCase();
		for (String temp : peticiones) {
			String corregido = temp.replace("@asistente", "@" + nombreLower);
			if (mensaje.matches(".*" + corregido + ".*")) {
				setear_Cordialidad(SubIndice, peticiones.size());
				return true;
			}
			SubIndice++;
		}
		return false;
	}

	protected String respuesta() {
		String select = "respuestas_" + this.getClass().getSimpleName();
		if (respuestas == null)
			cargarLista(select);
		if (respuestas == null)
			return null;
		return respuestas.get(subindice(respuestas));
	}

	private int subindice(ArrayList<String> temp) {
		int tamArray = temp.size() - 1;
		int desface = (int) (Math.random() * 5) - 2;
		int i = (int) (cordialidad * tamArray) + desface;
		return i > -1 ? i < tamArray ? i : tamArray : 0;
	}

	private void cargarLista(String select) {
		Scanner entrada = null;
		String dir = null;
		ArrayList<String> temp;
		if (select.contains("respuesta")) {
			temp = respuestas = new ArrayList<String>();
			dir = "Respuestas\\" + select + ".dat";
		} else {
			temp = peticiones = new ArrayList<String>();
			dir = "Peticiones\\" + select + ".dat";
		}
		try {
			entrada = new Scanner(new File(dir));
			while (entrada.hasNextLine()) {
				String nextLine = entrada.nextLine();
				if (nextLine.startsWith("\""))
					while (entrada.hasNextLine() && nextLine.endsWith("\""))
						nextLine += "\n" + entrada.nextLine();
				temp.add(nextLine);
			}
			entrada.close();
		} catch (Exception e) {
			try {
				entrada = new Scanner(new File(dir.replace("\\", "/")));
				while (entrada.hasNextLine()) {
					String nextLine = entrada.nextLine();
					if (nextLine.startsWith("\""))
						while (entrada.hasNextLine() && nextLine.endsWith("\""))
							nextLine += "\n" + entrada.nextLine();
					temp.add(nextLine);
				}
				entrada.close();
			} catch (Exception e1) {
				if (entrada != null)
					entrada.close();
				else {
					try {
						FileWriter file = new FileWriter(new File(dir));
						file.write("");
						file.close();
					} catch (IOException e11) {
					}
				}
				System.out.println("no se encontro el arhivo " + select);
			}
		}
	}

	private void setear_Cordialidad(double SubIndice, double TamArray) {
		if (TamArray == 1)
			return;
		double cordialidadTemp;
		double CordialidadEnviada = SubIndice / TamArray;
		if (cordialidad < 0)
			cordialidadTemp = CordialidadEnviada;
		else
			cordialidadTemp = ((CordialidadEnviada - cordialidad) / 3) + cordialidad;

		if (cordialidadTemp > 1)
			cordialidadTemp = 0.99;

		cordialidad = cordialidadTemp;
	}

}
