package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class JueguitoTest {

	public final static String USUARIO = "delucas";

	static Asistente ada;

	@BeforeClass
	public static void setup() {
		ada = new Asistente();
		escucha("Hola @Ada");
	}

	private static String escucha(String mensaje) {
		return ada.escuchar(USUARIO + ": " + mensaje).substring(4);
	}

	@Test
	public void pensando() {
		int dijo = 0;
		escucha("@ada jueguito");
		String resp = escucha("@ada jueguito bueno dale");
		Pattern regex = Pattern.compile(".* ([0-9]+).*");
		Matcher match = regex.matcher(resp);
		if (match.find())
			dijo = Integer.parseInt(match.group(1));
		int pensado = 56;
		while (dijo != pensado) {
			if (dijo > pensado)
				resp = escucha("@ada mas chico");
			else if (dijo < pensado)
				resp = escucha("@ada mas grande");
			else
				assertTrue(true);
			match = regex.matcher(resp);
			if (match.find())
				dijo = Integer.parseInt(match.group(1));
			else
				assertTrue(false);
			System.out.println(dijo);
		}
		if (dijo == pensado)
			if (escucha("@ada jueguito ganaste!").contains("GANE"))
				assertTrue(true);
	}

	@Test(timeout = 250)
	public void adivinando() {
		int dije = 66;
		int max = 9999;
		int min = 0;
		String resp;
		boolean corriendo = true;
		while (corriendo) {
			dije = (int) (Math.random() * (max - min)) + min;
			resp = escucha("@ada es " + dije + "?");
			System.out.println(dije);
			if (resp.contains("grande"))
				min = dije + 1;
			else if (resp.contains("chico"))
				max = dije - 1;
			else if (resp.contains("ganaste")) {
				assertTrue(true);
				corriendo = false;
			}
		}
	}

}
