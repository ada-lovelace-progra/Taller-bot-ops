package cs;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	private Socket socket = null;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;

	public Cliente(int puerto) {
		Scanner hosts;
		String host = null;
		String hostIntentados = "";
		boolean UltimoIntento = true;

		try {
			hosts = new Scanner(new File("hosts.dat"));
			while ((hosts.hasNextLine() || UltimoIntento) && socket == null)
				try {
					if (hosts.hasNextLine()) {
						host = hosts.nextLine();
						hostIntentados += host + "\r\n";
					} else {
						hosts.close();
						host = InetAddress.getLocalHost().getHostName();
						UltimoIntento = false;
					}
					System.out.println("Intentando con Host: " + host);
					socket = new Socket(InetAddress.getByName(host).getHostAddress(), puerto);
				} catch (Exception e) {
					socket = null;
				}
		} catch (Exception e1) {
		}
		try {
			if (socket == null) {
				System.out.println("No hay ningun chochino sever... asique soy mi propio server");
				new Thread() {
					public void run() {
						try {
							new Servidor(5050);
						} catch (Exception e) {
						}
					}
				}.start();

				Thread.sleep(500);
				socket = new Socket(InetAddress.getByName(InetAddress.getLocalHost().getHostName()).getHostAddress(),
						puerto);

			}
			if (!hostIntentados.contains(host)) {
				FileWriter asd = new FileWriter("hosts.dat");
				asd.write(host + "\r\n" + hostIntentados.substring(0, hostIntentados.length() - 2));
				asd.close();
			}
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataOutputStream.flush();
		} catch (Exception e) {
		}
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
