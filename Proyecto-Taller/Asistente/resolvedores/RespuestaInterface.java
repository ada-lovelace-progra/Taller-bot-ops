package resolvedores;

public interface RespuestaInterface {
	public void siguiente(RespuestaInterface sig);

	public String intentarResponder(String mensaje);
}
