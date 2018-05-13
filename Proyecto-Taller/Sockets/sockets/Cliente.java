package sockets;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Cliente {
	private Socket socket;
	private DataInputStream bufferDeEntrada = null;
	private DataOutputStream bufferDeSalida = null;
	Scanner teclado = new Scanner(System.in);
	final String COMANDO_TERMINACION = "salir()";

	public void conectar(String ip, int puerto) {
		try {
			socket = new Socket(ip, puerto);
			mostrarTexto("Conectado a :" + socket.getInetAddress().getHostName());
		} catch (Exception e) {
			mostrarTexto("Excepción al levantar conexión: " + e.getMessage());
			System.exit(0);
		}
		try {
			bufferDeEntrada = new DataInputStream(socket.getInputStream());
			bufferDeSalida = new DataOutputStream(socket.getOutputStream());
			bufferDeSalida.flush();
		} catch (IOException e) {
			mostrarTexto("Error en la apertura de flujos");
		}
	}

	private static void mostrarTexto(String s) {
//		System.out.println(s);
	}

	public void enviar(String s) {
		try {
			bufferDeSalida.writeUTF(s);
			bufferDeSalida.flush();
		} catch (IOException e) {
			mostrarTexto("IOException on enviar");
		}
	}

	public void cerrarConexion() {
		try {
			bufferDeEntrada.close();
			bufferDeSalida.close();
			socket.close();
			mostrarTexto("Conexión terminada");
		} catch (IOException e) {
			mostrarTexto("IOException on cerrarConexion()");
		} finally {
			System.exit(0);
		}
	}

	public String recibir() {
		try {
			return (String) bufferDeEntrada.readUTF();
		} catch (IOException e) {
		}
		return "";
	}
}