package armadores;

import resolvedores.Agradecer;
import resolvedores.BuscarInformacionWikiGoogle;
import resolvedores.CalculoString;
import resolvedores.Chuck;
import resolvedores.Clima;
import resolvedores.Despedida;
import resolvedores.Fecha;
import resolvedores.Gag9;
import resolvedores.Giphy;
import resolvedores.Jueguito;
import resolvedores.LeyesRobotica;
import resolvedores.RecordarEventos;
import resolvedores.Simpsons;
import resolvedores.Unidades_S_Metrico;

/**
 * Crea la cadena de responsabilidad de las respuestas.
 */
public class Crear {

	static public RespuestaGenerico Cadena() {

		Agradecer agradecer = new Agradecer();
		CalculoString calcular = new CalculoString();
		Chuck chuck = new Chuck();
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
		Gag9 gag = new Gag9();

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
		calcular.siguiente(gag);
		gag.siguiente(unidades);
		unidades.siguiente(despedida);
				
		return eventos;
	}

}
