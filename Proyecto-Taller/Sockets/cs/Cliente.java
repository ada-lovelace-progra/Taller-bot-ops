package cs;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	private Socket socket;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;

	public Cliente(String ip, int puerto) throws Exception {
		socket = new Socket(ip, puerto);
		dataInputStream = new DataInputStream(socket.getInputStream());
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
		dataOutputStream.flush();

		Scanner scanner = new Scanner(System.in);
		System.out.println("ingrese su nombre de usuario...\n");
		enviar(scanner.nextLine());
		scanner.close();
	}

	public void enviar(String mensaje) throws Exception {
		dataOutputStream.writeUTF(mensaje);
		dataOutputStream.flush();
	}

	public String recibir() throws Exception {
		String mensaje = dataInputStream.readUTF();
		if(mensaje.contains("codigoDeDespedida"))
			cerrar();
		return mensaje;
	}

	public void cerrar() throws Exception {
		dataInputStream.close();
		dataOutputStream.close();
		socket.close();
	}
}
