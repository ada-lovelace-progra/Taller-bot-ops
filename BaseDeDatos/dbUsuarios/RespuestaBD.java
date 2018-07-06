package dbUsuarios;

public class RespuestaBD {
	private int id;
	private String Usuario;
	private String passHasheada;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return Usuario;
	}

	public void setUsuario(String usuario) {
		Usuario = usuario;
	}

	public String getPassHasheada() {
		return passHasheada;
	}

	public void setPassHasheada(String passHasheada) {
		this.passHasheada = passHasheada;
	}

}
