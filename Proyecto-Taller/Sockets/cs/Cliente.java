package cs;

import java.io.IOException;
import java.net.Socket;

public class Cliente {

	private Socket socket;

	public Cliente(String ip, int puerto) {
		try {
			socket = new Socket(ip, puerto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
