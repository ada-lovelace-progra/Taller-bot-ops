package cs;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Hashtable;

import usuariosYAsistente.Asistente;

public class Servidor {
	private Socket servidor;
	private ServerSocket serversock;

	public Servidor(int puerto) throws Exception {
		serversock = new ServerSocket(puerto);
		while (true) {
			servidor = serversock.accept();
			new Hilo(servidor).start();
		}
	}
}

class Hilo extends Thread {
	private DataInputStream bufferDeEntrada = null;
	private DataOutputStream bufferDeSalida = null;
	private Socket socket;
	static private Hashtable<String, ArrayList<Socket>> lista = new Hashtable<String, ArrayList<Socket>>();
	static private Hashtable<String, Asistente> asistentePorCodChat = new Hashtable<String, Asistente>();
	static private Hashtable<String, Socket> usuarios = new Hashtable<String, Socket>();
	static private String usuariosConectados = "";
	private String codChat;
	private String leer;
	private int codChatLibres = 5;

	public Hilo(Socket server) throws Exception {
		socket = server;
		bufferDeSalida = new DataOutputStream(socket.getOutputStream());
		bufferDeEntrada = new DataInputStream(socket.getInputStream());
		String readUTF = bufferDeEntrada.readUTF();
		codChat = readUTF.substring(0, 4);
		if (!lista.containsKey(codChat)) {
			lista.put(codChat, new ArrayList<Socket>());
			if (!codChat.equals("0000"))
				asistentePorCodChat.put(codChat, new Asistente());
		}
		lista.get(codChat).add(socket);
		String usuario = readUTF.substring(4);

		if (!usuariosConectados.contains(usuario)) {
			usuariosConectados += usuario + "?";
		}
		if (codChat.equals("0000")) {
			usuarios.put(usuario, socket);
		}
		System.out
				.println(usuario + " conectado en el puerto: " + socket.getPort() + " pidio Sala de Chat: " + codChat);
	}

	private Thread mandarConectador = new Thread() {
		public void run() {
			try {
				while (true) {
					bufferDeSalida.writeUTF("----" + usuariosConectados);
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				System.out.println("error mandando usuariosConectados");
			}
		}
	};

	public void run() {

		if (!codChat.equals("0000"))
			try {
				while (true) {
					String mensaje = bufferDeEntrada.readUTF();
					if (mensaje.contains(":")) {
						System.out.println("Chat: " + mensaje.substring(0, 4) + " Usuario: "
								+ mensaje.substring(4, mensaje.indexOf(":")) + " Mensaje: "
								+ mensaje.substring(mensaje.indexOf(":")));
						reenviarATodos(mensaje);
						asistente(mensaje.substring(4), mensaje.substring(0, 4));
					}
				}
			} catch (Exception e) {
				e.getStackTrace();
				System.out.println("error con cod Chat: " + codChat + "  " + e.getCause());
			}
		else
			try {
				try {
					mandarConectador.start();
				} catch (Exception e) {
				}
				while (true) {
					leer = bufferDeEntrada.readUTF();
					System.out.println(leer);
					new peticionesNuevoChat().start();
				}
			} catch (Exception e) {
				System.out.println("falla en procesamiento por CodChat 0000 " + e.getMessage() + e.getCause());
			}
	}

	class peticionesNuevoChat extends Thread {
		public void run() {
			String temp = leer;
			try {
				if (temp.contains("nuevoChat")) { // si es peticion entro
					String[] mensaje = temp.split("\\|");
					String codChatNuevo = obtenerCodChat(); // obtengo un codigo no usado
					String usuarioBuscado = mensaje[1]; // a este flaco estoy llamando
					Socket socketTemp = usuarios.get(usuarioBuscado); // aca traigo el socket de dicho usuario
					// agarro el bufferDeSalida del usuario llamado
					DataOutputStream bufferSalidaTemp = new DataOutputStream(socketTemp.getOutputStream());
					// y por ese buffer le mando el comando para que levante y el codigo de chat
					// nuevo
					bufferSalidaTemp.writeUTF("levantarConexion" + codChatNuevo + mensaje[2]);
					bufferDeSalida.writeUTF(codChatNuevo); // le mando al usuario que pidio el chat el codigo
					// nuevo
					System.out.println(codChatNuevo);
				}
			} catch (Exception e) {
				System.out.println("error procesando peticion nuevo Chat");
			}
		}
	}

	private String obtenerCodChat() {
		return String.format("%04d", (int) (Math.random() * 999) + codChatLibres++);
	}

	private void reenviarATodos(String mensaje) throws Exception {
		DataOutputStream bufferDeSalida;
		ArrayList<Socket> listaTemp = lista.get(mensaje.substring(0, 4));
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
			ArrayList<Socket> listaTemp = lista.get(codChat);
			for (Socket socketTemp : listaTemp) {
				bufferDeSalida = new DataOutputStream(socketTemp.getOutputStream());
				bufferDeSalida.writeUTF(respuetas);
			}
		}
	}

	@SuppressWarnings("unused")
	private void cerrar() throws Exception {
		bufferDeEntrada.close();
		bufferDeSalida.close();
		socket.close();
	}
}