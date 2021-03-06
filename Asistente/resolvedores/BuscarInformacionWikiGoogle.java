package resolvedores;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import armadores.RespuestaGenerico;

/**
 * Resolvedor, busca informacion de Wikipedia.
 */
public class BuscarInformacionWikiGoogle extends RespuestaGenerico {

	@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			String regex = "";
			if (mensaje.contains("sobre"))
				regex = "sobre";
			else if (mensaje.contains("informacion de"))
				regex = "informacion de";
			else if (mensaje.contains("que es"))
				regex = "que es";

			Matcher tema = Pattern.compile(regex + " (.*)").matcher(mensaje);
			if (tema.find()) {
				String link = "http://www.google.com/search?&btnI=745&pws=0&q=wikipedia%20sobre%20";
				link += tema.group(1).replace(" ", "%20");
				String info = obtenerTituloYVistaPrevia(link);
				info = info.substring(3);
				if (info != null)
					return "Segun Wikipedia " + info;
			}
		}
		return null;
	}

	private String obtenerTituloYVistaPrevia(String url) {
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/4.76");
			connection.setConnectTimeout(15000);
			connection.setReadTimeout(15000);
			connection.setInstanceFollowRedirects(true);
			connection.connect();
			url = connection.getHeaderField("location");

		} catch (Exception e1) {
			e1.printStackTrace();
			if (Math.random() > 0.5)
				return "algunas vez viste una tortuga agitarse? yo tampoco vi que se pueda realizar una busqueda con tu cochino internet";
			if (Math.random() > 0.5)
				return "donald Trump no me dejo pasar";
			return "tu cochino internet mato wikipedia";
		}

		try {
			URL urlPagina = new URL(url);
			URLConnection urlConexion = urlPagina.openConnection();
			urlConexion.connect();

			InputStream inputStream = urlConexion.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader lector = new BufferedReader(inputReader);
			String linea = "";
			while ((linea = lector.readLine()) != null)
				if (linea.contains("<p>"))
					return linea;

		} catch (Exception e) {
			if (Math.random() > 0.5)
				return "algunas vez viste una tortuga agitarse? yo tampoco vi que se pueda realizar una busqueda con tu cochino internet";
			if (Math.random() > 0.5)
				return "donald Trump no me dejo pasar";
			return "tu cochino internet mato wikipedia";
		}

		return null;
	}

}
