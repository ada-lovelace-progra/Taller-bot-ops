package cs;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import usuariosYAsistente.Asistente;
import usuariosYAsistente.Usuario;

public class Hilo extends Thread {
	private DataInputStream bufferDeEntrada = null;
	private Socket socket;
	private int num;
	private Usuario user;
	static ArrayList<Socket> lista = new ArrayList<>();
	static Asistente asistente = new Asistente();

	public Hilo(Socket server) {
		socket = server;
		lista.add(socket);
		num = lista.size();
	}

	public void run() {
		try {
			bufferDeEntrada = new DataInputStream(socket.getInputStream());
			user = new Usuario(bufferDeEntrada.readUTF());
			System.out.println(user.nombre + " conectado");
			while (true) {
				String mensaje = bufferDeEntrada.readUTF();
				System.out.println(user.nombre + " envia: " + mensaje);
				responderATodos(mensaje);
				asistente(user.nombre + ": " + mensaje);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void responderATodos(String mensaje) throws IOException {
		DataOutputStream bufferDeSalida;
		for (Socket socketTemp : lista)
			if (!socketTemp.equals(socket)) {
				bufferDeSalida = new DataOutputStream(socketTemp.getOutputStream());
				bufferDeSalida.writeUTF(user.nombre + ": " + mensaje);
			}
	}

	private void asistente(String mensaje) throws Exception {
		String respuetas = asistente.escuchar(mensaje);
		if (respuetas != null) {
			DataOutputStream bufferDeSalida;
			for (Socket socketTemp : lista) {
				bufferDeSalida = new DataOutputStream(socketTemp.getOutputStream());
				bufferDeSalida.writeUTF(respuetas);
			}
		}
	}

	private void cerrar() throws Exception {
		lista.remove(num - 1);
		bufferDeEntrada.close();
		socket.close();
	}
}
