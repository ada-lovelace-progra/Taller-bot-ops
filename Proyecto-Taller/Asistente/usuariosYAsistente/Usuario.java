package usuariosYAsistente;

import java.net.InetAddress;
import java.util.Hashtable;
import cs.Cliente;

public class Usuario extends UsuarioGenerico {
	private Hashtable<Integer, Cliente> cliente = new Hashtable<>();

	public Usuario(String NombreUsuario) {
		nombre = NombreUsuario;
	}

	public void nuevoChat(int codChat) {
		if (!cliente.containsKey(codChat)) {
			// for (String ip : listarIPs())
			try {
				cliente.put(codChat, new Cliente(InetAddress.getByName("Fede-Net").getHostAddress(), 5050));
				// Cliente temp = new Cliente(ip, 5050);
				cliente.get(codChat).enviar(String.format("%04d", codChat) + nombre);
				return;
			} catch (Exception e) {
			}
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
		}
		return recibir.substring(4);
	}
}
