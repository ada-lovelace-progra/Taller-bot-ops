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
	private Socket socket;
	static private Hashtable<String, ArrayList<Socket>> lista = new Hashtable<String, ArrayList<Socket>>();
	static private Hashtable<String, Asistente> asistentePorCodChat = new Hashtable<String, Asistente>();

	public Hilo(Socket server) throws Exception {
		socket = server;

		bufferDeEntrada = new DataInputStream(socket.getInputStream());
		String readUTF = bufferDeEntrada.readUTF();

		String codChat = readUTF.substring(0, 4);
		if (!lista.containsKey(codChat)) {
			lista.put(codChat, new ArrayList<Socket>());
			asistentePorCodChat.put(codChat, new Asistente());
		}
		lista.get(codChat).add(socket);
		System.out.println(readUTF.substring(4) + " conectado en el puerto: " + socket.getPort()
				+ " pidio Sala de Chat: " + codChat);
	}

	public void run() {
		try {
			while (true) {
				String mensaje = bufferDeEntrada.readUTF();
				System.out.println(
						 "Chat: " + mensaje.substring(0, 4) +
						" Usuario: " + mensaje.substring(4, mensaje.indexOf(":")) +
						" Mensaje: " + mensaje.substring(mensaje.indexOf(":")));
				reenviarATodos(mensaje);
				asistente(mensaje.substring(4), mensaje.substring(0, 4));
			}
		} catch (Exception e) {
		}
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
		socket.close();
	}
}