package resolvedores;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.*;

public class Gag9 extends RespuestaGenerico {

	private Pattern regex = Pattern.compile("media/(.+)/gip");

	@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return obtenerTodo();
		return null;
	}

	private String obtenerTodo() {
		String url = "https://img-9gag-fun.9cache.com/photo/aQ3OXmq_460sv.mp4";
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
