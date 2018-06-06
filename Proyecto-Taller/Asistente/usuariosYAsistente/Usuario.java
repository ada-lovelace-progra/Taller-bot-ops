package usuariosYAsistente;

import java.net.InetAddress;
import java.util.Hashtable;
import cs.Cliente;

public class Usuario extends UsuarioGenerico {
	private Hashtable<Integer, Cliente> cliente = new Hashtable<>();

	public Usuario(String NombreUsuario) {
		nombre = NombreUsuario;
	}

	public int pedirNuevoChat(String userAConectar) {
		int codChat = 0;
		try {
			Cliente clienteTemp = new Cliente(InetAddress.getByName("LAB4B2").getHostAddress(), 5050);
			clienteTemp.enviar("0000nuevoChat" + userAConectar);
			String codTemp = recibir(0);
			if (codTemp != "----") {
				codChat = Integer.parseInt(codTemp);
				nuevoChat(codChat);
				return codChat;
			}
		} catch (Exception e) {
		}
		return -1;
	}

	public void nuevoChat(int codChat) {
		try {
			cliente.put(codChat, new Cliente(InetAddress.getByName("LAB4B2").getHostAddress(), 5050));
			cliente.get(codChat).enviar(String.format("%04d", codChat) + nombre);
			return;
		} catch (Exception e) {
		}
	}

	public void enviar(String codChat, String mensaje) throws Exception {
		enviar(codChat, mensaje);
	}

	public void enviar(int codChat, String mensaje) throws Exception {
		cliente.get(codChat).enviar(String.format("%04d", codChat) + nombre + ": " + mensaje);
	}

	public String recibir(int codChat) throws Exception {
		String recibir = cliente.get(codChat).recibir();
		if (recibir.endsWith("-99-00")) {
			cliente.get(codChat).cerrar();
			cliente.remove(codChat);
			return "";
		} else if (recibir.contains("levantarConexion")) {
			nuevoChat(Integer.parseInt(recibir.substring(20)));
		} else if (recibir.length() == 4)
			return recibir;
		return recibir.substring(4);
	}
}
