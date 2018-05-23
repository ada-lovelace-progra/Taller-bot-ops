package resolvedores;

public class Despedida extends RespuestaGenerico {

	public Despedida() {
		super();
		nombreDeLaClase = "";
	}

	public String intentarResponder(String mensaje) {
		return "quien te conoce?";
	}

}
