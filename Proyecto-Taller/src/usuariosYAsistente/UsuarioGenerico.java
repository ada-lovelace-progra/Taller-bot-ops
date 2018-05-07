package usuariosYAsistente;

import sockets.Cliente;

public abstract class UsuarioGenerico {
	public String nombre;
	Cliente laposta;

	public void enviarMensaje(String mensaje) {
		laposta.enviar(nombre + ": " + mensaje);
	}

	public String recibirMensaje() {
		return laposta.recibir();
	}
}
