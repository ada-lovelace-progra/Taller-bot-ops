package resolvedores;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.regex.*;

public class Giphy extends RespuestaGenerico {

	private Pattern regex = Pattern.compile("de (.*)");

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			Matcher tema = regex.matcher(mensaje);
			if (tema.find()) {
				return obtenerTodo(tema.group(1).replace(" ", "+"));
			}
		}
		return null;
	}

	private String obtenerTodo(String tema) {
		String url = "http://api.giphy.com/v1/gifs/random?api_key=yvyt1ie3dMcJOJzDj1Lp7okLFHIUXYfs&fmt=html&tag=";
		url += tema.replace(" ", "%20");

		try {
			URL urlPagina = new URL(url);
			URLConnection urlConexion = urlPagina.openConnection();
			urlConexion.connect();

			InputStream inputStream = urlConexion.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader lector = new BufferedReader(inputReader);
			String linea = "";
			Pattern regex = Pattern.compile("media/(.+)/gip");
			Matcher match;
			while ((linea = lector.readLine()) != null)
				if ((match = regex.matcher(linea)).find()) {
					String link = "https://i.giphy.com/media/" + match.group(1) + "/giphy.gif";
					return "<img src=\"" + link + "\" height=\"50\" width=\"50\" >";
				}
			lector.close();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}
}
