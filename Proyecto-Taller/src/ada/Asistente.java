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
	private String dir = ""; // se utiliza como auxiliar por si

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
					setear_Cordialidad(tabla.get(select), cod);
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
				if (consulta(clave, cad)) {
					///// Acciones Especiales
					if (clave == "despedidas")
						activo = false;
					if (clave == "cuenta")
						cuenta = true;
					///// Acciones Especiales
					return respuesta(clave);
				}
			}
			if (cad.matches(".*funcion.*resolver.*") || cad.matches(".*resolver.*funcion.*")) {
				cuenta = true;
				return "no hay problema. ingresala a continuacion";
			}
			if (cuenta)
				return "la funcion da: " + CalculoString.calcularFormat("2+2", "%.3f");

			cuenta = false;
		}
		if (consulta("llamadas", cad)) {
			activo = true;
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

	private int subindice(String select) {
		ArrayList<String> temp = tabla.get(select);
		int ret = 10000, sub = cordial(temp);
		while (ret >= temp.size())
			ret = (int) (sub);
		return ret;
	}

	private int subindice(ArrayList<String> temp) {
		int ret = 10000, sub = cordial(temp);
		while (ret >= temp.size())
			ret = (int) (sub);
		return ret;
	}

	private int cordial(ArrayList<String> temp) {
		return (int) (((double) this.cordialidad / tope_cordialidad) * temp.size());
	}

	private void setear_Cordialidad(ArrayList<String> temp, String cad) {
		if (this.cordialidad != -1)
			this.cordialidad += ((temp.indexOf(cad) * (tope_cordialidad / temp.size())) - this.cordialidad) / 3;
		else
			this.cordialidad = temp.indexOf(cad) * (tope_cordialidad / temp.size());
		if (this.cordialidad > tope_cordialidad)
			this.cordialidad = tope_cordialidad - 1;
	}

	private void cargarLista(String select) {
		Scanner entrada = null;
		try {
			tabla.put(select, (new ArrayList<String>()));
			entrada = new Scanner(
					new File((select.contains("respuesta") ? "Respuestas\\" : "Peticiones\\\\") + select + ".dat"));
			while (entrada.hasNextLine())
				tabla.get(select).add(entrada.nextLine());
			entrada.close();
		} catch (Exception e) {
			if (entrada != null)
				entrada.close();
			System.out.println("no se encontro el arhivo " + select);
		}
	}

}