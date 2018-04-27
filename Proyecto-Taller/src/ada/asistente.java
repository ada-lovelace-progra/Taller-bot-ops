package ada;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class asistente {
	private ArrayList<String> despedidasAceptables;
	private ArrayList<String> llamadasAceptables;
	private ArrayList<String> peticionesTiempo;
	private ArrayList<String> respuestaNose;
	private ArrayList<String> respuestaSalida;
	private ArrayList<String> respuestaTiempo;
	private ArrayList<String> respuesta_nombrada;

	private final int tope_cordialidad = 100;
	private int cordialidad;
	public boolean activo;
	public boolean cuenta;
	public String dir = "";

	public asistente() {
		this.despedidasAceptables = new ArrayList<String>();
		this.llamadasAceptables = new ArrayList<String>();
		this.peticionesTiempo = new ArrayList<String>();
		this.respuestaNose = new ArrayList<String>();
		this.respuestaTiempo = new ArrayList<String>();
		this.respuesta_nombrada = new ArrayList<String>();

		this.activo = false;
		this.cordialidad = -1;
		this.cuenta = false;
		cargarLista(llamadasAceptables, "llamadas.dat");
	}

	private String nombrada(String cad) {
		return respuesta_nombrada.get(subindice(respuesta_nombrada));
	}

	public String procesarMensaje(String entrada) {
		String cad = entrada.toLowerCase();
		cargaArraysGenerales();
		if (peticionesTiempo.contains(cad))
			return consultaTiempo(cad);
		else if (seDespideAlAsistente(cad))
			return respuesta_salida();
		else if (cad.matches(".*ad+a.*"))
			return nombrada(cad);
		else if (cad.matches(".*funcion.*resolver.*") || cad.matches(".*resolver.*funcion.*")) {
			cuenta = true;
			return "no hay problema. ingresala a continuacion";
		} else if (cuenta) {
			cuenta = false;
			return "la funcion da: " + CalculoString.calcular(cad);
		}
		return "nada";
	}

	private void cargaArraysGenerales() {
		if (despedidasAceptables.isEmpty())
			cargarLista(despedidasAceptables, "despedidas.dat");
		if (peticionesTiempo.isEmpty())
			cargarLista(peticionesTiempo, "peticion_tiempo.dat");
		if (peticionesTiempo.isEmpty())
			cargarLista(peticionesTiempo, "peticion_tiempo.dat");
		if (respuesta_nombrada.isEmpty())
			cargarLista(respuesta_nombrada, "respuestas_nombrada.dat");
	}

	private String respuesta_salida() {
		respuestaSalida = new ArrayList<String>();
		cargarLista(respuestaSalida, "saludos.dat");
		return respuestaSalida.get(subindice(respuestaSalida));
	}

	private String consultaTiempo(String cad) {
		setear_Cordialidad(peticionesTiempo, cad);
		if (respuestaTiempo.isEmpty())
			cargarLista(respuestaTiempo, "respuestas_tiempo.dat");
		return respuestaTiempo.get(subindice(respuestaTiempo));
	}

	private String Ay_Nose() {
		if (respuestaNose.isEmpty())
			cargarLista(respuestaNose, "respuestas_nose.dat");
		return respuestaNose.get(subindice(respuestaNose));
	}

	private int subindice(ArrayList<String> temp) {
		int ret = 10000;
		while (ret >= temp.size())
			ret = (int) ((2 - Math.random() % 5) + cordial(temp));
		return ret;
	}

	private int cordial(ArrayList<String> temp) {
		return (int) (((double) this.cordialidad / tope_cordialidad) * temp.size());
	}

	public boolean seDespideAlAsistente(String entrada) {
		String cad = entrada.toLowerCase();
		if (despedidasAceptables.contains(cad))
			activo = false;
		return !activo;
	}

	public boolean seLlamoAlAsistente(String entrada) {
		String cad = entrada.toLowerCase();
		if (llamadasAceptables.contains(cad)) {
			setear_Cordialidad(llamadasAceptables, cad);
			activo = true;
		}
		return activo;
	}

	private void setear_Cordialidad(ArrayList<String> temp, String cad) {
		if (this.cordialidad != -1)
			this.cordialidad += ((temp.indexOf(cad) * (tope_cordialidad / temp.size())) - this.cordialidad) / 3;
		else
			this.cordialidad = temp.indexOf(cad) * (tope_cordialidad / temp.size());
		if (this.cordialidad > tope_cordialidad)
			this.cordialidad = tope_cordialidad - 1;
	}

	private void cargarLista(ArrayList<String> temp, String dir) {
		Scanner entrada = null;
		try {
			entrada = new Scanner(new File(this.dir + dir));
			while (entrada.hasNextLine())
				temp.add(entrada.nextLine());
		} catch (FileNotFoundException e) {
		}
		entrada.close();
	}

}