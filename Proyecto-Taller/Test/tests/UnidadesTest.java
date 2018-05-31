package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import resolvedores.Unidades_S_Metrico;

class UnidadesTest {

	@Test
	void test() {
		Unidades_S_Metrico.cambio("56 kilogramos a litros");
	}

}
