package tests;

import org.junit.Assert;
import org.junit.Test;

import funcionesExtras.CalculoString;

public class CalculoStringTest {
	static String cad = "";

	@Test
	public void suma() {
		CalculoString cs = new CalculoString();
		cad = "2+105+8.5+3";
		double res = cs.calcular(cad);

		Assert.assertEquals(118.5, res, 0.1);
	}

	@Test
	public void resta() {
		CalculoString cs = new CalculoString();
		cad = "2-6-100-4";
		double res = cs.calcular(cad);

		Assert.assertEquals(-108, res, 0.1);
	}

	@Test
	public void producto() {
		CalculoString cs = new CalculoString();
		cad = "0.1*100*(2*-5)";
		double res = cs.calcular(cad);

		Assert.assertEquals(-100, res, 0.1);
	}

	@Test
	public void division() {
		CalculoString cs = new CalculoString();
		cad = "(1024/2)/-512/-1";
		double res = cs.calcular(cad);

		Assert.assertEquals(1, res, 0.1);
	}

	@Test
	public void potencia() {
		CalculoString cs = new CalculoString();
		cad = "(2^8)^-1";
		double res = cs.calcular(cad);

		Assert.assertEquals(1 / 256, res, 0.1);
	}

	@Test
	public void mixta() {
		CalculoString cs = new CalculoString();
		cad = "2+(100/50)*4-10+100";
		double res = cs.calcular(cad);

		Assert.assertEquals(100, res, 0.1);
	}

	@Test
	public void reJodida() {
		CalculoString cs = new CalculoString();
		cad = "(((1+1)^3*10)/80-6)*2-100-5+3";
		double res = cs.calcular(cad);

		Assert.assertEquals(-112, res, 0.1);
	}

	@Test
	public void numeroUnico() {
		CalculoString cs = new CalculoString();
		cad = "3";
		double res = cs.calcular(cad);

		Assert.assertEquals(3, res, 0.1);
	}

	@Test
	public void primeroNegativo() {
		CalculoString cs = new CalculoString();
		cad = "-1*(((1+1)^3*10)/80-6)*2";
		double res = cs.calcular(cad);

		Assert.assertEquals(10, res, 0.1);
	}

	@Test
	public void logaritmo() {
		CalculoString cs = new CalculoString();
		cad = "log(1000)";
		double res = cs.calcular(cad);

		Assert.assertEquals(Math.log(1000), res, 0.1);
	}

	@Test
	public void logaritmoConExtras() {
		CalculoString cs = new CalculoString();
		cad = "log(1000)";
		double res = cs.calcular(cad + "+10");
		Assert.assertEquals(Math.log(1000) + 10, res, 0.1);
		res = cs.calcular(cad + "-10");
		Assert.assertEquals(Math.log(1000) - 10, res, 0.1);
		res = cs.calcular(cad + "*10");
		Assert.assertEquals(Math.log(1000) * 10, res, 0.1);
		res = cs.calcular(cad + "/10");
		Assert.assertEquals(Math.log(1000) / 10, res, 0.1);
		res = cs.calcular("5+" + cad + "+10");
		Assert.assertEquals(5 + Math.log(1000) + 10, res, 0.1);
		res = cs.calcular("5-" + cad + "-10");
		Assert.assertEquals(5 - Math.log(1000) - 10, res, 0.1);
		res = cs.calcular("5*" + cad + "*10");
		Assert.assertEquals(5 * Math.log(1000) * 10, res, 0.1);
		res = cs.calcular("5/" + cad + "/10");
		Assert.assertEquals(5 / Math.log(1000) / 10, res, 0.1);
	}
}
