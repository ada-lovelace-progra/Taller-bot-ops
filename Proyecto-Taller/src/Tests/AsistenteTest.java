package Tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ada.Asistente;

public class AsistenteTest {

	public final static String USUARIO = "delucas";

	Asistente ada;

	@Before
	public void setup() {
		ada = new Asistente();
		ada.escuchar("hola ada");
	}

//	@Test
//	public void sinsentido() {
//		String[] mensajes = {
//				"Este mensaje no tiene sentido @ada"
//		};
//		/// se debe inicar el asistente
//		ada.escuchar("ada, necesito ayuda");
//		
//		for (String mensaje : mensajes) {
//		/*	Assert.assertEquals(
//			*		"Disculpa... no entiendo el pedido, @delucas ¿podrías repetirlo?",
//			*		ada.escuchar(mensaje));*/
//			Assert.assertEquals("como se realiza dicha peticion?", ada.escuchar(mensaje));
//		}
//	}
 
	@Test
	public void calculos() {
		Assert.assertEquals(
				"la funcion da: 3",
				ada.escuchar("@ada resolver 1+2")
			);
		
		Assert.assertEquals(
				"la funcion da: 1",
				ada.escuchar("@ada resolver 5-2*2")
			);
		
//		Assert.assertEquals(
//				"la funcion da: 10",
//				ada.escuchar("@ada resolver 10% de 100")
//			);
		
		Assert.assertEquals(
				"la funcion da: 42",
				ada.escuchar("@ada resolver 17+5^2")
			);
		
	}
	
	@Test
	public void calculosCompuestos() {
		Assert.assertEquals(
				"la funcion da: -112",
				ada.escuchar("@ada resolver (((1+1)^3*10)/80-6)*2-100-5+3")
			);
		
//		Assert.assertEquals(
//				"la funcion da: -6",
//				ada.escuchar("@ada resolver (4-8)*2+4/(1+1)")
//			);
	}
}
