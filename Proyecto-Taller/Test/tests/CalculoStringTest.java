package tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class CalculoStringTest {

	private static final String USUARIO = "fede";
	static Asistente ada;

	@BeforeClass
	public static void setup() {
		ada = new Asistente();
		escuchar("hola @ada");
	}

	private static String escuchar(String mensaje) {
		String escuchar = ada.escuchar(USUARIO + ": " + mensaje);
		if (escuchar.length() > 5)
			return escuchar.substring(4);
		return null;
	}

	@Test
	public void calculos() {
		Assert.assertEquals("Ada: La cuenta da: 3 @" + USUARIO, escuchar("@ada calcula 1+2"));

		Assert.assertEquals("Ada: La cuenta da: 1 @" + USUARIO, escuchar("@ada cuanto es 5-2*2"));

		Assert.assertEquals("Ada: La cuenta da: 42 @" + USUARIO, escuchar("@ada cuanto da 17+5^2"));

	}

	@Test
	public void calcularConNegativos() {
		Assert.assertEquals("Ada: La expresion da: 10 @" + USUARIO,
				escuchar("@ada resolve -1*(((1+1)^3*10)/80-6)*2"));
	}

	@Test
	public void calculosCompuestos() {
		Assert.assertEquals("Ada: La expresion da: -112 @" + USUARIO,
				escuchar("@ada resuelve (((1+1)^3*10)/80-6)*2-100-5+3"));
	}

	@Test
	public void porcentaje() {
		Assert.assertEquals("Ada: La cuenta da: 100 @" + USUARIO, escuchar("@ada cuanto da 25%400"));
	}

	@Test
	public void mixSupremo() {
		Assert.assertEquals("Ada: La expresion da: 15 @" + USUARIO, escuchar("@ada cuanto es 10%((30+20)+((135-30)-5)^1)"));
	}
}

/*
 * @Test public void logaritmo() { CalculoString cs = new CalculoString(); cad =
 * "log(1000)"; double res = cs.calcular(cad);
 * 
 * Assert.assertEquals(Math.log(1000), res, 0.1); }
 * 
 * @Test public void logaritmoConExtras() { CalculoString cs = new
 * CalculoString(); cad = "log(1000)"; double res = cs.calcular(cad + "+10");
 * Assert.assertEquals(Math.log(1000) + 10, res, 0.1); res = cs.calcular(cad +
 * "-10"); Assert.assertEquals(Math.log(1000) - 10, res, 0.1); res =
 * cs.calcular(cad + "*10"); Assert.assertEquals(Math.log(1000) * 10, res, 0.1);
 * res = cs.calcular(cad + "/10"); Assert.assertEquals(Math.log(1000) / 10, res,
 * 0.1); res = cs.calcular("5+" + cad + "+10"); Assert.assertEquals(5 +
 * Math.log(1000) + 10, res, 0.1); res = cs.calcular("5-" + cad + "-10");
 * Assert.assertEquals(5 - Math.log(1000) - 10, res, 0.1); res =
 * cs.calcular("5*" + cad + "*10"); Assert.assertEquals(5 * Math.log(1000) * 10,
 * res, 0.1); res = cs.calcular("5/" + cad + "/10"); Assert.assertEquals(5 /
 * Math.log(1000) / 10, res, 0.1); } }
 */