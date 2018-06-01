package resolvedores;

import pendientes.Despedida;
import pendientes.Fecha;
import pendientes.Unidades_S_Metrico;

public class Crear {

	static public RespuestaGenerico Cadena() {
		Agradecer agradecer = new Agradecer();
		CalculoString calcular = new CalculoString();
		ChuckNorris chuck = new ChuckNorris();
		LeyesRobotica leyes = new LeyesRobotica();
		Despedida despedida = new Despedida();
		Fecha fecha = new Fecha();
		Simpsons simpsons = new Simpsons();
		Unidades_S_Metrico unidades = new Unidades_S_Metrico();

		agradecer.siguiente(fecha);
		fecha.siguiente(leyes);
		leyes.siguiente(simpsons);
		simpsons.siguiente(chuck);
		chuck.siguiente(calcular);
		calcular.siguiente(unidades);
		unidades.siguiente(despedida);

		return agradecer;
	}

}
