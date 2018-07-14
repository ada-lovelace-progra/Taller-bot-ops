package plugins;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import bdRespuestas.MemesBD;

public class Codificaciones {

	static private String codificarYoutube(String recibido) {
		Matcher regex = Pattern.compile(";(\\S+);").matcher(recibido);
		String link = "";
		if (regex.find()) {
			link = regex.group(1);
			return recibido.replace(";" + link + ";",
					"<a href=\"www.youtube.com/watch?v=dQw4w9WgXcQ\">" + link + "</a>");
		}
		return recibido;
	}

	static private String codificarLink(String recibido) {
		String ini = "<a href=\"";
		String fin = "\">";
		Matcher regex = Pattern.compile("(http\\S+)").matcher(recibido);
		String link = "";
		if (regex.find()) {
			link = regex.group(1);
			return recibido.replace(link, ini + link + fin);
		}
		regex = Pattern.compile("(www\\S+)").matcher(recibido);
		if (regex.find()) {
			link = regex.group(1);
			return recibido.replace(link, ini + link + fin);
		}
		regex = Pattern.compile("(\\S+.\\S+)").matcher(recibido);
		if (regex.find()) {
			link = regex.group(1);
			return recibido.replace(link, ini + link + fin);
		}
		return recibido;
	}

	@SuppressWarnings("unused")
	static private boolean esYoutube2(String recibido) {
		return recibido.contains("youtu");
	}

	static private String codificarMeme(String recibido) {
		Matcher mat = Pattern.compile(":(.+):").matcher(recibido);
		String meme = "";
		if (mat.find()) {
			meme = mat.group(1);
			String path = new MemesBD().obtenerMeme(meme);
			if (path != null) {
				recibido = recibido.replace(":" + meme + ":", "meme:" + path);
			}
		}
		return recibido;
	}

	static private boolean esMeme(String recibido) {
		if (recibido.matches(":(.*):"))
			return true;
		return false;
	}

	static private String codificarImagen(String recibido) {
		Matcher regex = Pattern.compile("(www\\S+)").matcher(recibido);
		String link = "";
		if (regex.find()) {
			link = regex.group(1);
			return recibido.replace(link, "<img width=\"100\" height=\"50\" src=\"" + link + "\">");
		}
		regex = Pattern.compile("(http\\S+)").matcher(recibido);
		if (regex.find()) {
			link = regex.group(1);
			return recibido.replace(link, "<img width=\"100\" height=\"50\" src=\"" + link + "\">");
		}
		return recibido;
	}

	static private boolean esImagen(String recibido) {
		if (esLink(recibido))
			return recibido.contains("jpg") || recibido.contains("gif") /* || recibido.contains("png") */
					|| recibido.contains("img") || comprobarQueEsImagenDeFormaFea(recibido);
		else
			return false;
	}

	static private boolean comprobarQueEsImagenDeFormaFea(String recibido) {
		Matcher regex = Pattern.compile("(www\\S+)").matcher(recibido);
		String link = "";
		if (regex.find()) {
			link = regex.group(1);
			try {
				ImageIO.read(new URL(link));
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	static private boolean esLink(String recibido) {
		return recibido.contains("http") || recibido.contains("https") || recibido.contains("www.")
				|| recibido.contains(".com");
	}

	public static String codificar(String mensaje) {
		if (mensaje == null || mensaje.length() < 2)
			return mensaje;
		if (esImagen(mensaje))
			mensaje = codificarImagen(mensaje);
		else if (mensaje.matches(".*;.*;.*"))
			mensaje = codificarYoutube(mensaje);
		else if (esLink(mensaje))
			mensaje = codificarLink(mensaje);
		else if (esMeme(mensaje))
			mensaje = codificarMeme(mensaje);
		return mensaje;
	}

}
