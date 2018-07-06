package bdRespuestas;

public class RespuestaBD {
	private int id;
	private String clase;
	public String peticion;
	public String respuesta;


	public int getId() {
		return this.id;
	}

	public String getClase() {
		return this.clase;
	}

	public String getPeticion() {
		return this.peticion;
	}

	public String getRespuesta() {
		return this.respuesta;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public void setPeticion(String peticion) {
		this.peticion = peticion;
	}

	public void setRespuesta(String res) {
		this.respuesta = res;
	}
}
