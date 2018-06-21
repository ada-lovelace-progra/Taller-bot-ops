package resolvedores;

import java.io.*;
import java.net.*;
import java.util.regex.*;


/** 
 * Resolvedor, muestra gifs proporcionados por giphy.
 */
public class Giphy extends RespuestaGenerico {

	private Pattern regex = Pattern.compile("de (.*)");
	private Pattern regexGif = Pattern.compile("media/(.+)/gip");
	
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
		try {
			URL urlPagina = new URL(url);
			URLConnection urlConexion = urlPagina.openConnection();
			urlConexion.connect();

			InputStream inputStream = urlConexion.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader lector = new BufferedReader(inputReader);
			String linea = "";
			
			Matcher match;
			while ((linea = lector.readLine()) != null)
				if ((match = regexGif.matcher(linea)).find()) {
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
