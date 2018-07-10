package dbUsuarios;

public class RespuestaBD {
	private int id;
	private String usuario;
	private String pass;

	public RespuestaBD() {
	}

	public RespuestaBD(int id, String user, String pass) {
		this.id = id;
		this.usuario = user;
		this.pass = pass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
