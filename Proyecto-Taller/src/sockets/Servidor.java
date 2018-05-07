package sockets;

import java.net.*;

import usuariosYAsistente.Asistente;

import java.io.*;

public class Servidor{

	Asistente Asistente;
	
	private Socket socket;
	private ServerSocket serverSocket;
	private DataInputStream bufferDeEntrada = null;
	private DataOutputStream bufferDeSalida = null;

	public void Conectar(int puerto) {
		try {
			serverSocket = new ServerSocket(puerto);
			mostrarTexto("Esperando conexión entrante en el puerto " + String.valueOf(puerto) + "...");
			socket = serverSocket.accept();
			mostrarTexto("Conexión establecida con: " + socket.getInetAddress().getHostName() + "\n\n\n");
		} catch (Exception e) {
			mostrarTexto("Error en levantarConexion(): " + e.getMessage());
			System.exit(0);
		}
		try {
			bufferDeEntrada = new DataInputStream(socket.getInputStream());
			bufferDeSalida = new DataOutputStream(socket.getOutputStream());
			bufferDeSalida.flush();
		} catch (IOException e) {
			mostrarTexto("Error en la apertura de flujos");
		}
		
		Asistente = new Asistente();
	}

	public String recibir() {
		try {
			String mensaje = (String) bufferDeEntrada.readUTF();
			if(!Asistente.activo)
				enviar(Asistente.ActivarAda(mensaje));
			else
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
		System.out.println(s);
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