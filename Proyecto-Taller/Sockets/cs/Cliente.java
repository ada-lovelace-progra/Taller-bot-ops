package cs;

import java.io.*;
import java.net.Socket;

public class Cliente {

	private Socket socket;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;

	public Cliente(String ip, int puerto) throws Exception {
		socket = new Socket(ip, puerto);
		dataInputStream = new DataInputStream(socket.getInputStream());
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
		dataOutputStream.flush();
	}

	public void enviar(String mensaje) throws Exception {
		dataOutputStream.writeUTF(mensaje);
		dataOutputStream.flush();
	}

	public String recibir() throws Exception {
		return dataInputStream.readUTF();
	}

	public void cerrar() throws Exception {
		dataInputStream.close();
		dataOutputStream.close();
		socket.close();
	}
}
