package cs;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	private Socket socket = null;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;
	private static String host = null;

	public Cliente(int puerto) {
		if (host == null) {
			if (levantarConexion(puerto))
				levantarServerYConectarse(puerto);
		} else
			try {
				socket = new Socket(InetAddress.getByName(host).getHostAddress(), puerto);
			} catch (Exception e) {
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
		Scanner hosts = null;

		try {
			conectar(puerto, InetAddress.getLocalHost());
		} catch (Exception e2) {
		}

		if (socket == null) {
			try {
				hosts = new Scanner(new File("hosts.dat"));
			} catch (Exception e1) {
			}
			String hostIntentados = "";
			while (socket == null && hosts.hasNextLine())
				try {
					host = hosts.nextLine();
					hostIntentados += host + "\r\n";
					InetAddress posibleHost = InetAddress.getByName(host);
					if (posibleHost.isReachable(100))
						conectar(puerto, posibleHost);
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
			hosts.close();
		}
		return true;
	}

	private void conectar(int puerto, InetAddress host) throws UnknownHostException, IOException {
		socket = new Socket(host.getHostAddress(), puerto);
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
