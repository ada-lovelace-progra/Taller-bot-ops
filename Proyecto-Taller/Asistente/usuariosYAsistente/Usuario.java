package usuariosYAsistente;

import java.net.InetAddress;
import java.util.Hashtable;
import cs.Cliente;

public class Usuario extends UsuarioGenerico {
//	private static final String HOST_NAME = "Fede-Net";
	private static final String HOST_NAME = "LAB4B2";
	private Hashtable<Integer, Cliente> cliente = new Hashtable<>();
	private String codTemp;
	public static String usuariosConectados = "";

	public Usuario(String NombreUsuario) {
		nombre = NombreUsuario;
		usuariosConectados += nombre + "?";
	}

	public int pedirNuevoChat(String userAConectar) {
		int codChat = 0;
		try {
			System.out.println("Solicitando nuevo Chat");
			codTemp = "0000";
			cliente.get(0).enviar("0000nuevoChat" + userAConectar);
			while (codTemp == "0000") {
				System.out.print(".");
				Thread.sleep(300);
			}
			if (codTemp != "----") {
				codChat = Integer.parseInt(codTemp);
				Cliente clienteTemp = new Cliente(InetAddress.getByName(HOST_NAME).getHostAddress(), 5050);
				clienteTemp.enviar(codTemp + nombre);
				cliente.put(codChat, clienteTemp);
				cliente.get(codChat).enviar(codTemp + nombre);
				return codChat;
			}
		} catch (Exception e) {
		}
		return -1;
	}

	public void nuevoChat(int codChat) {
		try {
			System.out.println("intentando levantar conexion... CodChat: " + codChat);
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
		}
		if (codChat == 0) {
			// System.out.println(recibir);
			if (recibir.matches("[0-9]+")) {
				codTemp = recibir;
				System.out.println(codTemp);
				return "";
			} else if (recibir.contains("levantarConexion")) {
				System.out.println("levantando");
				String subCadena = recibir.substring(16);
				nuevoChat(Integer.parseInt(subCadena));
				return recibir;
			} else if (recibir.contains("?")) {
				return recibir.substring(4);
			}
		}
		return recibir.substring(4);
	}
}
