package cs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import usuariosYAsistente.Asistente;

public class HiloServer extends Thread {

	private DataInputStream bufferDeEntrada = null;
	private DataOutputStream bufferDeSalida = null;
	private Socket socket;
	static private Hashtable<String, ArrayList<Socket>> listaSocketPorCodChat = new Hashtable<String, ArrayList<Socket>>();
	static private Hashtable<String, Asistente> asistentePorCodChat = new Hashtable<String, Asistente>();
	static private Hashtable<String, Socket> socketPorUsuario = new Hashtable<String, Socket>();
	static private Hashtable<String, String> codChatPorSala = new Hashtable<String, String>();
	static private Asistente asistenteEventos = new Asistente();
	static private String usuariosConectados = "";
	private String codChat;
	private int codChatLibres = 5;
	public boolean iniciado = false;
	private String usuario;

	public HiloServer(Socket server) throws Exception {
		socket = server;
		bufferDeSalida = new DataOutputStream(socket.getOutputStream());
		bufferDeEntrada = new DataInputStream(socket.getInputStream());
		String readUTF = bufferDeEntrada.readUTF();

		if (readUTF.startsWith("0000")) {
			// comprobar si está registrado
			// ejemplo de lectura (readUTF): 0000Usuario|PassHash
			String user_pass = readUTF.substring(4);
			if (user_pass.charAt(0) == '$') {
				user_pass = user_pass.replace("$", "");
				crearUsuario(user_pass);
			}
			comprobarUsuario(user_pass);

			readUTF = readUTF.substring(0, readUTF.indexOf("|"));
			if (!iniciado)
				return;
		}
		/////////////////////////////////////////////////////////////////

		cargaCodChat(readUTF);
		usuario = cargaUsuario(readUTF);
		if (codChat.equals("0000") && !hiloEventos.isAlive())
			hiloEventos.start();
	}

	private boolean crearUsuario(String user_pass) {
		String user = user_pass.substring(0, user_pass.indexOf("|"));
		String pass = user_pass.substring(user_pass.indexOf("|") + 1);

		if (!dbUsuarios.BaseDato.comprobarUser(user))// Puedo crear el usuario si no existe
		{
			dbUsuarios.BaseDato.crearUsuario(user, pass);
			return true;
		}
		return false;
	}

	private void comprobarUsuario(String user_pass) throws IOException {
		String user = user_pass.substring(0, user_pass.indexOf("|"));
		String pass = user_pass.substring(user_pass.indexOf("|") + 1);
		if (!new dbUsuarios.BaseDato().traerDatos(user, pass)) {
			iniciado = false;
			System.out.println("No se encontró el usuario: " + user);
			bufferDeSalida.writeUTF("No se encontró el usuario");
			return;
		}
		iniciado = true;
		bufferDeSalida.writeUTF("iniciado");
	}

	private String cargaUsuario(String readUTF) {
		if (readUTF.contains("#"))
			return null;

		String usuario = readUTF.substring(4);

		if (!usuariosConectados.contains(usuario)) {
			usuariosConectados += usuario + "?";
		}
		if (codChat.equals("0000")) {
			socketPorUsuario.put(usuario, socket);
		}
		return usuario;
	}

	private void cargaCodChat(String readUTF) {
		codChat = readUTF.substring(0, 4);
		if (!listaSocketPorCodChat.containsKey(codChat)) {
			listaSocketPorCodChat.put(codChat, new ArrayList<Socket>());
			if (!codChat.equals("0000"))
				asistentePorCodChat.put(codChat, new Asistente());
		}
		listaSocketPorCodChat.get(codChat).add(socket);
	}

	private Thread mandarConectados = new Thread() {
		public void run() {
			try {
				while (true) {
					bufferDeSalida.writeUTF("----" + usuariosConectados);
					Thread.sleep(500);
				}
			} catch (Exception e) {
				cerrar();
			}
		}
	};

	public void run() {
		// mensajes entre usuarios
		if (codChat != null && !codChat.equals("0000"))
			try {
				while (true) {
					String mensaje = bufferDeEntrada.readUTF();
					System.out.println(mensaje);
					if (mensaje.contains(":")) {
						mensaje = mensaje.substring(0, 4) + procesarPosibleInvitacion(mensaje.substring(4));
						cambioDeNombre(mensaje);
						reenviarATodos(mensaje);
						asistente(mensaje.substring(4), mensaje.substring(0, 4));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				cerrar();
			}
		/// mensajes "bajo nivel" entre server y cliente
		else if (iniciado)
			trabajoABajoNivel();
	}

	private void cambioDeNombre(String mensaje) {
		if (mensaje.contains("#T")) {
			String codChat = mensaje.substring(0, 4);
			for (java.util.Map.Entry<String, String> temp : codChatPorSala.entrySet())
				if (codChat.equals(temp.getValue())) {
					codChatPorSala.remove(temp.getKey());
					String nuevoNombre = "#" + mensaje.substring(mensaje.indexOf("=") + 1);
					codChatPorSala.put(nuevoNombre, codChat);
					usuariosConectados = usuariosConectados.replace(temp.getKey(), nuevoNombre);
					return;
				}
		}
	}

	private void trabajoABajoNivel() {
		try {
			mandarConectados.start();
			Pattern regexAgregar = Pattern.compile("agregarSala(.*)1([0-9]{4})");
			Pattern regexSacar = Pattern.compile("agregarSala(.*)0([0-9]{4})");

			while (true) {
				String leer = bufferDeEntrada.readUTF();
				System.err.println(leer);
				new peticionesNuevoChat(leer).start();

				agregarSalaALista(regexAgregar, leer);
				sacarSalaDeLista(regexSacar, leer);

			}
		} catch (Exception e) {
			cerrar();
		}
	}

	private void sacarSalaDeLista(Pattern regexSacar, String leer) {
		Matcher match = regexSacar.matcher(leer);
		if (match.find()) {
			String sala = match.group(1);
			usuariosConectados = usuariosConectados.replace(sala + "?", "");
			if (codChatPorSala.contains(sala))
				codChatPorSala.remove(sala);
		}
	}

	private void agregarSalaALista(Pattern regex, String leer) {
		Matcher match = regex.matcher(leer);
		if (match.find()) {
			String sala = match.group(1);

			if (usuariosConectados.contains(sala))
				return;

			usuariosConectados += sala + "?";
			codChatPorSala.put(sala, match.group(2));
		}
	}

	private String procesarPosibleInvitacion(String mensaje) {
		if (mensaje.contains("@") && mensaje.contains("#")) {
			Matcher regex = Pattern.compile("@(\\S+)#").matcher(mensaje);
			if (regex.find())
				try {
					Socket socketTemp = socketPorUsuario.get(regex.group(1));
					DataOutputStream bufferSalidaTemp = new DataOutputStream(socketTemp.getOutputStream());
					Matcher regex1 = Pattern.compile("(#\\S+)[1,0]").matcher(mensaje);
					String nombreSala = "";
					if (regex1.find())
						nombreSala = regex1.group(1);
					bufferSalidaTemp.writeUTF("levantarConexion" + codChat + nombreSala);

					return mensaje.substring(0, mensaje.lastIndexOf("#"));
				} catch (Exception e) {
					return mensaje;
				}
		}
		return mensaje;
	}

	class peticionesNuevoChat extends Thread {
		private String leer;

		public peticionesNuevoChat(String leer) {
			this.leer = leer;
		}

		public void run() {
			try {

				// ejemplo de nuevo chat:
				// 0000nuevoChat|Nueva_Sala|usuarioquelapidio
				if (leer.contains("nuevoChat")) { // si es peticion entro

					String[] mensaje = leer.split("\\|");

					if (mensaje[1].charAt(0) != '#') { // si pido a un usuario

						// obtengo un codigo no usado
						String codChatNuevo = obtenerCodChat();

						if (!mensaje[1].equals("Nueva_Sala"))
							enviarAlUsuarioLlamado(mensaje, codChatNuevo);

						// le mando al usuario que pidio el chat el codigo nuevo
						bufferDeSalida.writeUTF(codChatNuevo);

					} else {// si pido a una sala
						bufferDeSalida.writeUTF(codChatPorSala.get(mensaje[1]));
					}

				}
			} catch (Exception e) {
			}
		}

		private void enviarAlUsuarioLlamado(String[] mensaje, String codChatNuevo) throws IOException {
			String usuarioBuscado = mensaje[1]; // a este flaco estoy llamando
			// agarro el bufferDeSalida del usuario llamado
			Socket socketTemp = socketPorUsuario.get(usuarioBuscado); // aca traigo el socket de dicho usuario
			DataOutputStream bufferSalidaTemp = new DataOutputStream(socketTemp.getOutputStream());
			// y por ese buffer le mando el comando para que levante y el codigo de chat
			// nuevo
			bufferSalidaTemp.writeUTF("levantarConexion" + codChatNuevo + mensaje[2]);
		}
	}

	private String obtenerCodChat() {
		codChatLibres += (int) (Math.random() * 19);
		return String.format("%04d", codChatLibres);
	}

	private void reenviarATodos(String mensaje) throws Exception {
		DataOutputStream bufferDeSalida;
		ArrayList<Socket> listaTemp = listaSocketPorCodChat.get(mensaje.substring(0, 4));
		for (Socket socketTemp : listaTemp)
			if (socketTemp.getPort() != socket.getPort()) {
				bufferDeSalida = new DataOutputStream(socketTemp.getOutputStream());
				bufferDeSalida.writeUTF(mensaje);
			}
	}

	private void asistente(String mensaje, String codChat) throws Exception {
		String respuetas = asistentePorCodChat.get(codChat).escuchar(mensaje);
		if (!respuetas.contains("null")) {
			DataOutputStream bufferDeSalida;
			ArrayList<Socket> listaTemp = listaSocketPorCodChat.get(codChat);
			for (Socket socketTemp : listaTemp) {
				bufferDeSalida = new DataOutputStream(socketTemp.getOutputStream());
				bufferDeSalida.writeUTF(respuetas);
			}
		}
	}

	Thread hiloEventos = new Thread() {
		public void run() {
			while (true) {
				String respuetas = asistenteEventos.getEvento(usuario);
				if (respuetas != null && !respuetas.contains("null")) {
					try {
						bufferDeSalida.writeUTF(respuetas);
					} catch (IOException e) {
					}
				}
			}
		}
	};

	private void cerrar() {
		if (codChat.equals("0000")) {
			String clave = usuario + "?";
			usuariosConectados = usuariosConectados.replace(clave, "");
		}
		try {
			bufferDeEntrada.close();
			bufferDeSalida.close();
			socket.close();
		} catch (Exception e) {
		}
	}
}