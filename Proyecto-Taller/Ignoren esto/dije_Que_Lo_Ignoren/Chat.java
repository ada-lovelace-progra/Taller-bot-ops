package usuariosYAsistente;

import java.util.*;

/// si bien eso no se va a usar ahora... como flashee que si, lo cree para hacer que un usuario tenga varios chats... igual no se si estara bien posta

public class Chat {
	Hashtable<Usuario, ArrayList<String>> Mensajes;
	ArrayList<Usuario> usuarios;
	UsuarioGenerico AditaQuerida = new Asistente();

	public Chat(Usuario[] users) {

		usuarios = new ArrayList<Usuario>();
		Mensajes = new Hashtable<Usuario, ArrayList<String>>();
		for (Usuario User : users) {
			agregarUsuario(User);
		}
	}

	public void agregarUsuario(Usuario user) {
		usuarios.add(user);
		Mensajes.put(user, new ArrayList<String>());
	}

	public boolean cargarMensaje(String userEnvia, String Mensaje) {
		boolean resultado = true;
		Mensaje = userEnvia + ": " + Mensaje;
		for (Usuario user : usuarios)
			if (user.nombre != userEnvia) {
				if (Mensajes.contains(user))
					Mensajes.put(user, new ArrayList<String>());
				resultado = (Mensajes.get(user).add(Mensaje) && resultado) ? true : false;
			}

		return resultado;
	}

	public String recibirMensaje(UsuarioGenerico usuarioGenerico) {
		while (Mensajes.get(usuarioGenerico).isEmpty())
			try {
				Thread.sleep(500);
			} catch (Exception e) {
			}
		String mensaje = Mensajes.get(usuarioGenerico).get(0);
		Mensajes.get(usuarioGenerico).remove(0);
		return mensaje;
	}
}
