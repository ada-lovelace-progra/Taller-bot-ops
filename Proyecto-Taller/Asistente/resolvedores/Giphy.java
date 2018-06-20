package resolvedores;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.regex.*;

public class Giphy extends RespuestaGenerico {

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			Matcher tema = Pattern.compile("de (.*)").matcher(mensaje);
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
			while ((linea = lector.readLine()) != null)
				if (linea.contains("img")) {
					String link = linea.substring(26, linea.lastIndexOf("><") - 2);
					String htmlImagen = linea.substring(linea.indexOf("<im"), linea.indexOf("/>") + 2);
					try {
						saveImage(htmlImagen.substring(10,htmlImagen.length()-3), "C:\\Users\\fedem\\Escritorio\\giphy.gif");
					} catch (IOException e) {
					}
					return link + "\n" + htmlImagen;
				}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}

	public void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL website = new URL(imageUrl);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(destinationFile);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}

}
