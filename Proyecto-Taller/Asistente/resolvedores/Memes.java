package resolvedores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Memes extends RespuestaGenerico {

	Pattern regex = Pattern.compile(".*;.*;.*");

	@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje)) {
			Matcher match = regex.matcher(mensaje);
			if (match.find())
				return obtenerMeme(match.group(1));
		}
		return null;
	}

	private String obtenerMeme(String meme) {
		return meme;
	}

}
