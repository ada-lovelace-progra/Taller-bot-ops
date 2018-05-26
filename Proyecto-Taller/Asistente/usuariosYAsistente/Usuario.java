package usuariosYAsistente;

import java.net.InetAddress;
import cs.Cliente;

public class Usuario extends UsuarioGenerico {
	private Cliente cliente;

	public Usuario(String NombreUsuario, String codChat) throws Exception {
		nombre = NombreUsuario;
		cliente = new Cliente("localhost", 5050);
		cliente.enviar(codChat + nombre);
	}

	public Usuario(String NombreUsuario, int codChat) throws Exception {
		nombre = NombreUsuario;
		cliente = new Cliente(InetAddress.getByName("LAB4A2").getHostAddress(), 5050);
		cliente.enviar(String.format("%04d", codChat) + nombre);
	}

	public void enviar(String codChat, String mensaje) throws Exception {
		enviar(codChat, mensaje);
	}

	public void enviar(int codChat, String mensaje) throws Exception {
		cliente.enviar(String.format("%04d", codChat) + nombre + ": " + mensaje);
	}

	public String recibir() throws Exception {
		String recibir = cliente.recibir();
		return recibir.substring(4);
	}
}
