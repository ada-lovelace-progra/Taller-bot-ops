package probadoresAntiLucas;

import java.net.InetAddress;

import cs.Servidor;

public class serverTesteador {

	static public void main(String a[]) throws Exception {
		new Servidor(5050);
		System.out.println("salio");
	}
}
