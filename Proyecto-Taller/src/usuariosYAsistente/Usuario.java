package usuariosYAsistente;

import sockets.Cliente;

public class Usuario extends UsuarioGenerico {

	public Usuario(String NombreUsuario) {
		nombre = NombreUsuario;
		laposta=new Cliente();
		laposta.conectar("localhost", 5050);
	}

	public Usuario(String NombreUsuario, int puerto) {
		nombre = NombreUsuario;
		super.laposta.conectar("localhost", puerto);
	}

	public Usuario(String NombreUsuario, String ip, int puerto) {
		nombre = NombreUsuario;
		super.laposta.conectar(ip, puerto);
	}
}
