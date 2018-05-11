package sockets;

import java.net.*;
import usuariosYAsistente.Asistente;

import java.io.*;

public class Servidor {
	private Asistente Asistente;
	private Socket socket;
	private ServerSocket serverSocket;
	private DataInputStream bufferDeEntrada = null;
	private DataOutputStream bufferDeSalida = null;

//	private void tratamientoPorCliente() {while (true)recibir();}

	public void Conectar(int puerto) {
		boolean op = true;
		try {
			serverSocket = new ServerSocket(puerto);
			mostrarTexto("Esperando conexión entrante en el puerto " + String.valueOf(puerto) + "...");
			socket = serverSocket.accept();
			mostrarTexto("Conexión establecida con: " + socket.getInetAddress().getHostName() + "\n\n\n");
			if (op) {
				Asistente = new Asistente();
				op = false;
			}

		} catch (Exception e) {
			mostrarTexto("Error en levantarConexion(): " + e.getMessage());
			System.exit(0);
		}
		try {
			bufferDeEntrada = new DataInputStream(socket.getInputStream());
			bufferDeSalida = new DataOutputStream(socket.getOutputStream());
			bufferDeSalida.flush();
		//	tratamientoPorCliente();
		} catch (IOException e) {
			mostrarTexto("Error en la apertura de flujos");
		}

	}

	public String recibir() {
		try {
			String mensaje = bufferDeEntrada.readUTF();
			enviar(Asistente.escuchar(mensaje));
			return mensaje;
		} catch (IOException e) {
			cerrarConexion();
		}
		return "";
	}

	public void enviar(String s) {
		try {
			bufferDeSalida.writeUTF(s);
			bufferDeSalida.flush();
		} catch (IOException e) {
			mostrarTexto("Error en enviar(): " + e.getMessage());
		}
	}

	private void mostrarTexto(String s) {
		// System.out.println(s);
	}

	public void cerrarConexion() {
		try {
			bufferDeEntrada.close();
			bufferDeSalida.close();
			socket.close();
		} catch (IOException e) {
			mostrarTexto("Excepción en cerrarConexion(): " + e.getMessage());
		} finally {
			mostrarTexto("Conversación finalizada....");
			System.exit(0);

		}
	}
}