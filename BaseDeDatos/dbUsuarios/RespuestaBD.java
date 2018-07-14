package dbUsuarios;

public class RespuestaBD {
	private String usuario;
	private String pass;

	public RespuestaBD() {
	}

	public RespuestaBD(String user, String pass) {
		this.usuario = user;
		this.pass = pass;
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
