package usuariosYAsistente;

import sockets.Cliente;

public class Usuario extends UsuarioGenerico {

	Cliente laposta = new Cliente();

	public Usuario(String NombreUsuario) {
		nombre = NombreUsuario;
		laposta.conectar("localhost", 5050);
	}

	public Usuario(String NombreUsuario, int puerto) {
		nombre = NombreUsuario;
		laposta.conectar("localhost", puerto);
	}

	public Usuario(String NombreUsuario, String ip, int puerto) {
		nombre = NombreUsuario;
		laposta.conectar(ip, puerto);
	}

	public void enviarMensaje(String mensaje) {
		laposta.enviar(nombre + ": " + mensaje);
	}

	public String recibirMensaje() {
		return laposta.recibir();
	}
}
