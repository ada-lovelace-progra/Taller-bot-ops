package usuariosYAsistente;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

import funcionesExtras.CalculoString;
import funcionesExtras.Fecha;
import sockets.Cliente;

import java.util.Scanner;

public class Asistente extends UsuarioGenerico {
	private Hashtable<String, ArrayList<String>> tabla, esperado;
	private final int tope_cordialidad = 100;
	private int cordialidad;
	public boolean activo;
	private boolean cuenta;
	private boolean seteado = false;

	public Asistente() {
		laposta = new Cliente();
		laposta.conectar("localhost", 5050);
	}

	private void seteoInicial() {
		seteado = true;
		nombre = "Ada Lovelace";
		tabla = new Hashtable<String, ArrayList<String>>();
		esperado = new Hashtable<String, ArrayList<String>>();
		this.cuenta = false;
		this.cordialidad = -1;
		cargarLista("llamadas");
	}

	public String escuchar(String entrada) {
		return "Ada Lovelace: " + limpio(entrada);
	}

	public String limpio(String entrada) {
		/*
		 * tring entrada = ""; while (entrada.length() < 4) { entrada =
		 * recibirMensaje(); }
		 */
		String cad = entrada.replace("@ada", "").substring(entrada.indexOf(":") + 2);
		String user = " @" + entrada.substring(0, entrada.indexOf(":"));
		{/// RESPUESTAS ESPERADAS
			String aux = respuestas_esperadas_del_usuario(cad);
			if (aux.length() > 1) {
				enviarMensaje(aux);
				return aux;
			}
		} /// Y SI NO RESPONDIO LO ESPERADO PRUEBO CON LO DE SIEMPRE

		/// cargo los archivos con las peticiones comunes... conviene hacer esto aca asi
		/// en caso de justo agregar archivos de peticiones mientras se corre el
		/// asistente se cargan igual
		cargarPeticionesGenerales();
		for (Entry<String, ArrayList<String>> ArraysEnLaHashtable : tabla.entrySet()) {
			String clave = ArraysEnLaHashtable.getKey(); /// esto es para ver que archivo o que tipo de peticion
															/// es
			if (!clave.contains("respuestas") && !clave.contains("nombrada"))
				/// si contiene respuesta no entro... esto es para optimizar y no evaluar las
				/// respuestas ya que no contienen ninguna peticion
				/// no entro por nombrada porque capaz se nombra para pedirle una accion onda
				/// "ada decime el tiempo"... y la idea es que entre en tiempo y no en nombrada

				if (consulta(clave, cad)) { // aca evaluo si el mensaje esta contenido en los arraylist de cada
											// clave
					///// Acciones Especiales
					/// estas acciones son para las peticiones mas complejas y que necesitan una
					///// respuesta especial
					switch (clave) {

					case "simpsons":
						/// en este caso no paso por la funcion respuesta() ya que no quiero cambiar la
						/// cordialidad y quiero cargar el archivo de simpson por separado
						cargarLista("respuestas_simpsons");
						return (tabla.get("respuestas_" + clave).get(tabla.get(clave).indexOf(cad)));

					case "despedidas":
						/// si la despido borro la hashtable para no ocupar espacio y cambio el estado
						/// de activo
						activo = false;
						tabla.clear();
						/// tambien cargo la lista de llamadas para poder volver a llamarla
						cargarLista("llamadas");
						return (respuesta(clave));

					case "cuenta":
						// esto es por si la cuenta va por separado... pero creo que va a terminar
						// borrandose...
						String aux = cad.substring(cad.lastIndexOf(" "));
						return ("la " + (aux.length() < 12 ? "cuenta" : "exprecion") + " da: "
								+ new CalculoString().calcularFormat(aux, "%.3f") + user);

					case "fecha":
						return ("hoy es " + Fecha.getFechaCompleta() + user);

					case "hora":
						return ("son las " + Fecha.getHora() + user);

					case "todo_bien":
						String[] lista = {"todobien"};
						cargarListaEsperadas(lista);
						///// Acciones Especiales
					default:
						return (respuesta(clave));

					}
				}

		}
		{ // abria que meter esto en los archivos... los archivos de peticion los evalua
			// con expreciones regulares asique metiendo lo que dice en los archivo y
			// clavando los if con el nombre del arhcivo para que ejecute la respuesta
			// deciada ya alcanzaria
			if (cad.matches(".*gracias.*")) {
				return ("no es nada" + user);

			}
		}
		if (entrada.contains("@ada")) {
			return (respuesta("nose"));

		}
		if (consulta("nombrada", cad)) {
			/// una vez que se que no entro en ningun caso evaluado me fijo si fue nombrada
			return (respuesta("nombrada") + user);

		}
		if (cuenta) {
			// esto no estaria muy bien que digamos porque va con la peticion anterior
			return ("la funcion da: " + new CalculoString().calcularFormat("2+2", "%.3f"));

		}
		cuenta = false;
		return "";
	}

	public String ActivarAda(String cad) {
		cad = cad.toLowerCase();
		if (!seteado)
			seteoInicial();
		if (!activo && consulta("llamadas", cad.toLowerCase())) {
			activo = true;
			tabla.get("llamadas").clear();
			return "hola";
		}
		return "";
	}

	private boolean consulta(String select, String cod) {
		if (tabla.containsKey(select))
			for (String temp : tabla.get(select))
				if (cod.matches(temp) || cod.contains(temp)) {
					if (!select.contains("simpsons"))
						setear_Cordialidad(tabla.get(select).indexOf(temp), tabla.get(select).size());
					return true;
				}
		return false;
	}

	private boolean consultaEsperadas(String select, String cod) {
		if (esperado.containsKey(select))
			for (String temp : esperado.get(select))
				if (cod.matches(temp)) {
					setear_Cordialidad(esperado.get(select).indexOf(temp), esperado.get(select).size());
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

	private String respuestaEsperadas(String select) {
		select = "respuestas_" + select;
		ArrayList<String> temp = null;
		if (!esperado.containsKey(select)) {
			cargarListaEsperadas(select);
			if (esperado.containsKey(select))
				temp = esperado.get(select);
		} else
			temp = esperado.get(select);
		if (temp == null)
			temp = esperado.get("No_se");
		return temp.get(subindice(temp));
	}

	private String respuestas_esperadas_del_usuario(String cad) {
		if (!esperado.isEmpty()) {
			for (Entry<String, ArrayList<String>> temp : esperado.entrySet()) {
				String clave = temp.getKey();
				if (!clave.contains("respuestas") && consultaEsperadas(clave, cad)) {
					///// Acciones Especiales

					///// Acciones Especiales
					return respuestaEsperadas(clave);
				}
			}
			esperado.clear();
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

	private int subindice(ArrayList<String> temp) {
		double porcentajeDeCordialidad = (double) this.cordialidad / (double) this.tope_cordialidad;
		int tamArray = temp.size() - 1;
		int desface = (int) ((Math.random() * 10) % 3) - 1;
		int i = (int) (porcentajeDeCordialidad * tamArray);
		i += desface;
		return i > -1 ? i < tamArray ? i : tamArray : 0;
	}

	private void setear_Cordialidad(double SubIndice, double TamArray) {
		if (TamArray == 1)
			return;
		TamArray--;
		int CordialidadEnviada = (int) ((SubIndice / TamArray) * tope_cordialidad);
		if (this.cordialidad < 0)
			this.cordialidad = CordialidadEnviada;
		else
			this.cordialidad = ((CordialidadEnviada - this.cordialidad) / 3) + this.cordialidad;

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

	private void cargarListaEsperadas(String[] listaDeCarga) {
		for (String select : listaDeCarga)
			cargarListaEsperadas(select);
	}

	private void cargarListaEsperadas(String select) {
		Scanner entrada = null;
		try {
			tabla.put(select, (new ArrayList<String>()));
			entrada = new Scanner(new File("Esperadas"
					+ (select.contains("respuesta") ? "Respuestas\\" : "Peticiones\\\\") + select + ".dat"));
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