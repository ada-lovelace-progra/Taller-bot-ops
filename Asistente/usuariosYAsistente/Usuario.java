package usuariosYAsistente;

import java.util.Hashtable;
import cs.Cliente;

public class Usuario extends UsuarioGenerico {
	private Hashtable<Integer, Cliente> clientePorCodChat = new Hashtable<>();
	private String codTemp;
	private String pass;

	public Usuario(String NombreUsuario) {
		nombre = NombreUsuario;
	}

	public Usuario(String NombreUsuario, String hash) {
		this(NombreUsuario);
		this.pass = hash;
	}

	public int pedirNuevoChat(String userAConectar) {
		int codChat = 0;
		try {
			System.out.println("Solicitando nuevo Chat");
			codTemp = "0000";
			clientePorCodChat.get(0).enviar("0000nuevoChat" + "|" + userAConectar + "|" + nombre);
			while (codTemp == "0000") {
				System.out.print(".");
				Thread.sleep(300);
			}
			if (codTemp != "----") {
				codChat = Integer.parseInt(codTemp);
				Cliente clienteTemp = new Cliente(5050);
				clienteTemp.enviar(codTemp + nombre);
				clientePorCodChat.put(codChat, clienteTemp);
				clientePorCodChat.get(codChat).enviar(codTemp + nombre);
				return codChat;
			}
		} catch (Exception e) {
		}
		return -1;
	}

	public boolean nuevoChat(int codChat) {
		try {
			System.out.println("intentando levantar conexion... CodChat: " + codChat);
			clientePorCodChat.put(codChat, new Cliente(5050));
			if (codChat == 0) {
				clientePorCodChat.get(codChat).enviar(String.format("%04d", codChat) + nombre + "|" + pass);
				return clientePorCodChat.get(codChat).recibir().equals("iniciado");
			} else
				clientePorCodChat.get(codChat).enviar(String.format("%04d", codChat) + nombre);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void enviar(int codChat, String mensaje) throws Exception {
		clientePorCodChat.get(codChat).enviar(String.format("%04d", codChat) + nombre + ": " + mensaje);
	}

	public String recibir(int codChat) throws Exception {
		String recibir = clientePorCodChat.get(codChat).recibir();
		if (recibir.endsWith("-99-00")) {
			clientePorCodChat.get(codChat).cerrar();
			clientePorCodChat.remove(codChat);
			return "";
		}
		if (codChat == 0) {
			if (recibir.matches("[0-9]+")) {
				codTemp = recibir;
				System.out.println(codTemp);
				return "";
			} else if (recibir.contains("levantarConexion")) {
				System.out.println("levantando");
				String codChatNuevo = recibir.substring(16, 20);
				nuevoChat(Integer.parseInt(codChatNuevo));
				return recibir;
			} else if (recibir.contains("?")) {
				return recibir.substring(4);
			}
		}
		return recibir.substring(4);
	}
}
