package resolvedores;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Giphy extends RespuestaGenerico {

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			Matcher tema = Pattern.compile("de (.*)").matcher(mensaje);
			if (tema.find())
				return obtenerTodo(tema.group(1).replace(" ", "+"));
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
			while ((linea = lector.readLine()) != null)
				if (linea.contains("img")) {
					String link = linea.substring(26, linea.lastIndexOf("><") - 2);
					String htmlImagen = linea.substring(linea.indexOf("<im"), linea.indexOf("/>") + 2);
					return link + "\n" + htmlImagen;
				}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}

}
