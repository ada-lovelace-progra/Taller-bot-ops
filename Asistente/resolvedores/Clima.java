package resolvedores;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import armadores.RespuestaGenerico;


/** 
 * Resolvedor, devuelve el clima.
 */
public class Clima extends RespuestaGenerico {

	public String intentarResponder(String mensaje) {
		if (mensaje.contains("clima"))
			return obtenerClima();
		return null;
	}

	private String obtenerClima() {
		String url = "http://ipinfo.io/geo";
		System.out.println("Leyendo Pagina : " + url);

		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			InputStream inputStream = connection.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader lector = new BufferedReader(inputReader);
			String linea = "";
			while ((linea = lector.readLine()) != null)
				if (linea.contains("city")) {
					lector.close();
					String loc = linea.substring(linea.indexOf(": \"") + 3, linea.lastIndexOf("\""));
					{
						url = "http://api.openweathermap.org/data/2.5/weather?q=";
						url += loc.replace(" ", "%20");
						url += "&mode=html&APPID=f16e2ec1254648d6eb482a622267ecd8";
						connection = (HttpURLConnection) new URL(url).openConnection();
						inputStream = connection.getInputStream();
						inputReader = new InputStreamReader(inputStream, "UTF-8");
						lector = new BufferedReader(inputReader);
						linea = "";
						String resp = "";
						int i = 0;
						while ((linea = lector.readLine()) != null) {
							if (++i > 11)
								resp += linea;
							if (i == 27) {
								lector.close();
								resp = resp.replace("Humidity", "Humedad");
								resp = resp.replace("Wind", "Viento");
								resp = resp.replace("Pressure", "Preson");
								resp = resp.replace("Clouds", "Nubosidad");
								return resp;
							}
						}
					}
				}

			lector.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return null;
	}

}
