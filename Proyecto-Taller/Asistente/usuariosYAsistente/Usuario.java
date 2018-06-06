package usuariosYAsistente;

import java.net.InetAddress;
import java.util.Hashtable;
import cs.Cliente;

public class Usuario extends UsuarioGenerico {
	private static final String HOST_NAME = "Fede-Net";
	private Hashtable<Integer, Cliente> cliente = new Hashtable<>();

	public Usuario(String NombreUsuario) {
		nombre = NombreUsuario;
	}

	public int pedirNuevoChat(String userAConectar) {
		int codChat = 0;
		try {
			cliente.get(0).enviar("0000nuevoChat" + userAConectar);
			String codTemp = recibir(0);
			if (codTemp != "----") {
				Cliente clienteTemp = new Cliente(InetAddress.getByName(HOST_NAME).getHostAddress(), 5050);
				clienteTemp.enviar(String.format("%04d", codChat) + nombre);
				codChat = Integer.parseInt(codTemp);
				cliente.put(codChat, clienteTemp);
				cliente.get(codChat).enviar(String.format("%04d", codChat) + nombre);
				return codChat;
			}
		} catch (Exception e) {
		}
		return -1;
	}

	public void nuevoChat(int codChat) {
		try {
			cliente.put(codChat, new Cliente(InetAddress.getByName(HOST_NAME).getHostAddress(), 5050));
			cliente.get(codChat).enviar(String.format("%04d", codChat) + nombre);
		} catch (Exception e) {
		}
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
			String subCadena = recibir.substring(20);
			nuevoChat(Integer.parseInt(subCadena));
			return subCadena;
		} else if (recibir.length() == 4)
			return recibir;
		return recibir.substring(4);
	}
}
