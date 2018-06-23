package vistas;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Codificaciones {

	
	static private String codificarYoutube(String recibido) {
		new YouTube();
		Matcher asd = Pattern.compile(";(\\S+);").matcher(recibido);
		String link = "";
		if (asd.find()) {
			link = asd.group(1);
			return recibido.replace(";" + link + ";",
					"<a href=\"www.youtube.com/watch?v=dQw4w9WgXcQ\">" + link + "</a>");
		}
		return recibido;
	}

	static private String codificarLink(String recibido) {
		String ini = "<a href=\"";
		String fin = "\">";
		Matcher asd = Pattern.compile("(http\\S+)").matcher(recibido);
		String link = "";
		if (asd.find()) {
			link = asd.group(1);
			// return recibido.replace(link, ini + link + fin + link);
			return recibido.replace(link, ini + link + fin);
			// return recibido.replace(link,obtenerTituloYVistaPrevia(link));
		}
		asd = Pattern.compile("(www\\S+)").matcher(recibido);
		if (asd.find()) {
			link = asd.group(1);
			// return recibido.replace(link, ini + link + fin + link);
			return recibido.replace(link, ini + link + fin);
		}
		asd = Pattern.compile("(\\S+.\\S+)").matcher(recibido);
		if (asd.find()) {
			link = asd.group(1);
			// return recibido.replace(link, ini + "www." + link + fin + link);
			return recibido.replace(link, ini + link + fin);
		}
		return recibido;
	}

	@SuppressWarnings("unused")
	static private boolean esYoutube2(String recibido) {
		return recibido.contains("youtu");
	}

	static private String codificarImagen(String recibido) {
		Matcher asd = Pattern.compile("(www\\S+)").matcher(recibido);
		String link = "";
		if (asd.find()) {
			link = asd.group(1);
			return recibido.replace(link, "<img width=\"100\" height=\"50\" src=\"" + link + "\">");
		}
		asd = Pattern.compile("(http\\S+)").matcher(recibido);
		if (asd.find()) {
			link = asd.group(1);
			return recibido.replace(link, "<img width=\"100\" height=\"50\" src=\"" + link + "\">");
		}
		return recibido;
	}

	static private boolean esImagen(String recibido) {
		if (esLink(recibido))
			return recibido.contains("jpg") || recibido.contains("gif") || recibido.contains("png")
					|| recibido.contains("img") || comprobarQueEsImagenDeFormaFea(recibido);
		else
			return false;
	}

	static private boolean comprobarQueEsImagenDeFormaFea(String recibido) {
		Matcher asd = Pattern.compile("(www\\S+)").matcher(recibido);
		String link = "";
		if (asd.find()) {
			link = asd.group(1);
			try {
				ImageIO.read(new URL(link));
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	static private boolean esLink(String recibido) {
		return recibido.contains("http") || recibido.contains("https") || recibido.contains("www")
				|| recibido.contains(".com");
	}

	static String codificar(String mensaje) {
		if (esImagen(mensaje))
			mensaje = codificarImagen(mensaje);
		else if (mensaje.matches(".*;.*;.*"))
			mensaje = codificarYoutube(mensaje);
		else if (esLink(mensaje)) {
			mensaje = codificarLink(mensaje);
		}
		return mensaje;
	}

}
