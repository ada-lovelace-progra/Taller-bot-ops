package resolvedores;

import java.awt.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Giphy extends RespuestaGenerico {

	public String intentarResponder(String mensaje) {
		// if (consulta(mensaje)) {
		if (mensaje.contains("giphy")) {
			String tema = "teclado";
			return obtenerTodo(tema);
		}
		return null;
	}

	private String obtenerTodo(String tema) {
		String url = "https://giphy.com/search/" + tema.replace(" ", "%20");

		try {
			URL urlPagina = new URL(url);
			URLConnection urlConexion = urlPagina.openConnection();
			urlConexion.connect();

			InputStream inputStream = urlConexion.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader lector = new BufferedReader(inputReader);
			String linea = "";
			ArrayList<String> listaGifs = new ArrayList<>();
			String regex = "(<img class=\".*\" src=\".*\" alt=\".*\">)";
			while ((linea = lector.readLine()) != null) {
				Matcher asd = Pattern.compile(regex).matcher(linea);
				System.out.println(linea);
				if (asd.find()) {
					listaGifs.add(asd.group(1));
				}
			}
			return listaGifs.get((int) (Math.random() * listaGifs.size()));
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

		return "";
	}

}
