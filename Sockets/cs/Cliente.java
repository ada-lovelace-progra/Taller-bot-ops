package cs;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	private Socket socket = null;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;
	private static String host = null;

	public Cliente(int puerto) {
		if (host == null) {
			if(levantarConexion(puerto))
				levantarServerYConectarse(puerto);
		} else
			try {
				System.out.println("Intentando con Host: " + host);
				socket = new Socket(InetAddress.getByName(host).getHostAddress(), puerto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		definirBuffer();
	}

	private void definirBuffer() {
		try {
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataOutputStream.flush();
		} catch (Exception e) {
		}
	}

	private void levantarServerYConectarse(int puerto) {
		System.out.println("No hay ningun chochino sever... asique soy mi propio server");
		new Thread() {
			public void run() {
				try {
					new Servidor(puerto);
				} catch (Exception e) {
				}
			}
		}.start();
		try {
			Thread.sleep(500);
			socket = new Socket(InetAddress.getByName(host).getHostAddress(), puerto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean levantarConexion(int puerto) {
		boolean UltimoIntento = true;
		Scanner hosts = null;
		try {
			hosts = new Scanner(new File("hosts.dat"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		String hostIntentados = "";
		while (UltimoIntento && socket == null)
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
				if (!hostIntentados.contains(host)) {
					try {
						FileWriter escribir = new FileWriter("hosts.dat");
						escribir.write(host + "\r\n" + hostIntentados.substring(0, hostIntentados.length() - 2));
						escribir.close();				
					} catch (Exception e) {
						e.printStackTrace();
					}
				}				
				
				return false;
			} catch (Exception e) {
				socket = null;
			}
		return true;
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
