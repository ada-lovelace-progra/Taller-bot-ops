package cs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Hilo extends Thread {
	private DataInputStream bufferDeEntrada = null;
	private DataOutputStream bufferDeSalida = null;
	private Socket socket;

	public Hilo(Socket server) {
		socket = server;
	}

	public void run() {
		try {

			bufferDeSalida = new DataOutputStream(socket.getOutputStream());
			bufferDeEntrada = new DataInputStream(socket.getInputStream());
			while (true) {
				bufferDeEntrada.readUTF();
				bufferDeSalida.writeUTF("facu se la come");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
