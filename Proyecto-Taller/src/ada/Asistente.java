package ada;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Scanner;
import ada.CalculoString;

public class Asistente {
	private Hashtable<String, ArrayList<String>> tabla;
	private final int tope_cordialidad = 100;
	private int cordialidad;
	public boolean activo;
	private boolean cuenta;

	public Asistente() {
		tabla = new Hashtable<String, ArrayList<String>>();
		this.activo = this.cuenta = false;
		this.cordialidad = -1;
		cargarLista("llamadas");
	}

	private boolean consulta(String select, String cod) {
		if (tabla.containsKey(select))
			for (String temp : tabla.get(select))
				if (cod.matches(temp)) {
					setear_Cordialidad(tabla.get(select).indexOf(cod), tabla.get(select).size());
					return true;
				}
		return false;
	}

	private String respuesta(String select) {
		select = "respuestas_" + select;
		ArrayList<String> temp = null;
		if (!tabla.containsKey(select)) {
			cargarLista(select);
			if (tabla.containsKey(select))
				temp = tabla.get(select);
		} else
			temp = tabla.get(select);
		if (temp == null)
			temp = tabla.get("No_se");
		return temp.get(subindice(temp));
	}

	public String procesarMensaje(String entrada) {
		String cad = entrada.toLowerCase();
		if (activo) {
			cargarPeticionesGenerales();
			for (Entry<String, ArrayList<String>> temp : tabla.entrySet()) {
				String clave = temp.getKey();
				if (!clave.contains("nombrada") && consulta(clave, cad)) {
					///// Acciones Especiales
					if (clave == "despedidas")
						activo = false;
					if (clave == "cuenta")
						cuenta = true;
					///// Acciones Especiales
					return respuesta(clave);
				}
			}
			if(consulta("nombrada", cad))
				return respuesta("nombrada");
			if (cuenta)
				return "la funcion da: " + new CalculoString().calcularFormat("2+2", "%.3f");
			cuenta = false;
		}
		if (consulta("llamadas", cad)) {
			activo = true;
			tabla.get("llamadas").clear();
			return respuesta("llamadas");
		}
		return "";
	}

	private void cargarPeticionesGenerales() {
		for (File file : (new File("Peticiones\\")).listFiles()) {
			String nombre = file.getName().toString();
			nombre = nombre.substring(0, nombre.length() - 4);
			if (!tabla.containsKey(nombre))
				cargarLista(nombre);
		}
	}

	/*
	 * private int subindice(ArrayList<String> temp) { int ret = 10000, sub =
	 * cordial(temp); while (ret >= temp.size()) ret = sub; return ret; }
	 */
	private int subindice(ArrayList<String> temp) {
		return cordial(temp);
	}

	private int cordial(ArrayList<String> temp) {
		int i = (int) (((double) this.cordialidad / tope_cordialidad) * (temp.size() - 1));
		return i > -1 ? i : 0;
	}

	private void setear_Cordialidad(ArrayList<String> temp, String cad) {
		if (this.cordialidad > 0)
			this.cordialidad += ((temp.indexOf(cad) * (tope_cordialidad / temp.size())) - this.cordialidad) / 3;
		else
			this.cordialidad = temp.indexOf(cad) * (tope_cordialidad / temp.size());
		if (this.cordialidad > tope_cordialidad)
			this.cordialidad = tope_cordialidad - 1;
	}

	private void setear_Cordialidad(int i, int t) {
		if (t == 1)
			return;
		if (this.cordialidad > 0)
			this.cordialidad = (i * (tope_cordialidad / t) - this.cordialidad);
		else
			this.cordialidad = i * (tope_cordialidad / t);
		if (this.cordialidad > tope_cordialidad)
			this.cordialidad = tope_cordialidad - 1;
	}

	private void cargarLista(String select) {
		Scanner entrada = null;
		try {
			tabla.put(select, (new ArrayList<String>()));
			entrada = new Scanner(
					new File((select.contains("respuesta") ? "Respuestas\\" : "Peticiones\\\\") + select + ".dat"));
			while (entrada.hasNextLine()) {
				String nextLine = entrada.nextLine();
				tabla.get(select).add(nextLine);
			}
			entrada.close();
		} catch (Exception e) {
			if (entrada != null)
				entrada.close();
			System.out.println("no se encontro el arhivo " + select);
		}
	}
}