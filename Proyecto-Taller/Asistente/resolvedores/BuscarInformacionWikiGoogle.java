package resolvedores;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuscarInformacionWikiGoogle extends RespuestaGenerico {

	@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			Matcher asd = Pattern.compile("sobre (.*)").matcher(mensaje);
			if (asd.find()) {
				String tema = asd.group(1);
				String info = obtenerTituloYVistaPrevia(
						"http://www.google.com/search?&btnI=745&pws=0&q=wikipedia%20sobre%20"
								+ tema.replace(" ", "%20"));
				return "Segun Wikipedia " + info.substring(3, info.length() - 4);
			}
		}
		return null;
	}

	private String obtenerTituloYVistaPrevia(String url) {
		System.out.println("Leyendo Pagina : " + url);

		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/4.76");
			connection.setConnectTimeout(15000);
			connection.setReadTimeout(15000);
			connection.setInstanceFollowRedirects(true);
			connection.connect();
			System.out.println(connection.getHeaderField("Location"));
			url = connection.getHeaderField("location");

		} catch (Exception e1) {
			e1.printStackTrace();
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
			System.out.println(e.getLocalizedMessage());
		}

		return "";
	}

}
