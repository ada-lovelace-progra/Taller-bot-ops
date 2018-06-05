package usuariosYAsistente;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;

import cs.Cliente;

public class Usuario extends UsuarioGenerico {
	private Hashtable<Integer, Cliente> cliente = new Hashtable<>();

	public Usuario(String NombreUsuario) {
		nombre = NombreUsuario;
	}

	public void nuevoChat(int codChat) throws Exception, UnknownHostException {
		if (!cliente.containsKey(codChat)) {
			cliente.put(codChat, new Cliente(InetAddress.getByName("Fede-Net").getHostAddress(), 5050));
			cliente.get(codChat).enviar(String.format("%04d", codChat) + nombre);
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
		return recibir.substring(4);
	}
}
