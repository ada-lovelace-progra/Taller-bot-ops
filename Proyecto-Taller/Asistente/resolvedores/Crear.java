package resolvedores;

public class Crear {

	static public RespuestaGenerico Cadena() {

		Agradecer agradecer = new Agradecer();
		CalculoString calcular = new CalculoString();
		ChuckNorris chuck = new ChuckNorris();
		Despedida despedida = new Despedida();
		Fecha fecha = new Fecha();
		LeyesRobotica asimov = new LeyesRobotica();
		RecordarEventos eventos = new RecordarEventos();
		Simpsons simpsons = new Simpsons();
		Unidades_S_Metrico unidades = new Unidades_S_Metrico();
		BuscarInformacionWikiGoogle wikipediaGoogle = new BuscarInformacionWikiGoogle();
		Giphy gif = new Giphy();
		Jueguito juego = new Jueguito();
		Clima clima = new Clima();

		eventos.siguiente(agradecer);
		agradecer.siguiente(clima);
		clima.siguiente(gif);
		gif.siguiente(juego);
		juego.siguiente(wikipediaGoogle);
		wikipediaGoogle.siguiente(fecha);
		fecha.siguiente(simpsons);
		simpsons.siguiente(chuck);
		chuck.siguiente(asimov);
		asimov.siguiente(calcular);
		calcular.siguiente(unidades);
		unidades.siguiente(despedida);

		return eventos;
	}

}
