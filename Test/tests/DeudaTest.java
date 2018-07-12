package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class DeudaTest {

	public final static String USUARIO = "delucas";

	Asistente jenkins;

	@Before
	public void setup() {
		jenkins = new Asistente();
		jenkins.escuchar(USUARIO + ": hola @jenkins");
	}

	@Test
	public void transferenciaDeDeudas() {
		Assert.assertEquals("Jenkins: Deuda agendada @delucas", jenkins.escuchar(USUARIO + ": @juan me debe $50 @jenkins").substring(4));
		Assert.assertEquals("Jenkins: @juan te debe $50 @delucas", jenkins.escuchar(USUARIO + ": cuánto me debe @juan? @jenkins").substring(4));
		Assert.assertEquals("Jenkins: Deuda agendada @delucas", jenkins.escuchar(USUARIO + ": le debo $60 a @maria @jenkins").substring(4));

		Assert.assertEquals("Jenkins: @juan te debe $50, le debés $60 a @maria @delucas",
				jenkins.escuchar(USUARIO + ": cual es mi estado de deudas? @jenkins").substring(4));

		Assert.assertEquals("Jenkins: listo. @delucas",
				jenkins.escuchar(USUARIO + ": simplificar deudas con @juan y @maria @jenkins").substring(4));

		Assert.assertEquals("Jenkins: le debés $10 a @maria @delucas",
				jenkins.escuchar(USUARIO + ": cual es mi estado de deudas? @jenkins").substring(4));
		// por detrás, ahora @juan le debe $50 a @maria. Podría probarse,
		// cambiando el interlocutor del asistente
	}

	@Test
	public void deudasGrupalesCasoUno() {

		Assert.assertEquals("Jenkins: gasto grupal anotado @delucas",
				jenkins.escuchar(USUARIO + ": con @juan y @maria gastamos $300 y pagó @juan @jenkins").substring(4));

		Assert.assertEquals("Jenkins: @maria te debe $90 @delucas",
				jenkins.escuchar(USUARIO + ": cual es mi estado de deudas? @jenkins").substring(4));
		// @maria le debe otros $100 a @juan

	}

	@Test
	public void deudasGrupalesCasoDos() {
		Assert.assertEquals("Jenkins: gasto grupal anotado @delucas",
				jenkins.escuchar(USUARIO + ": con @juan y @maria gastamos $300 y pagué yo @jenkins").substring(4));

		Assert.assertEquals("Jenkins: @juan te debe $100, @maria te debe $90 @delucas",
				jenkins.escuchar(USUARIO + ": cual es mi estado de deudas? @jenkins").substring(4));
	}

	@Test
	public void deudasSimples() {
		Assert.assertEquals("Jenkins: Deuda agendada @delucas", jenkins.escuchar(USUARIO + ": @juan me debe $500 @jenkins").substring(4));
		Assert.assertEquals("Jenkins: @juan te debe $500 @delucas",
				jenkins.escuchar(USUARIO + ": cuánto me debe @juan? @jenkins").substring(4));

		Assert.assertEquals("Jenkins: Pago agendado @delucas", jenkins.escuchar(USUARIO + ": @juan me pagó $501 @jenkins").substring(4));
		Assert.assertEquals("Jenkins: @juan no te debe nada, le debés $1 a @juan @delucas",
				jenkins.escuchar(USUARIO + ": cuánto me debe @juan? @jenkins").substring(4));
		Assert.assertEquals("Jenkins: le debés $1 a @juan @delucas",
				jenkins.escuchar(USUARIO + ": cuánto le debo a @juan? @jenkins").substring(4));

		Assert.assertEquals("Jenkins: Pago agendado @delucas", jenkins.escuchar(USUARIO + ": le pagué a @juan $10 @jenkins").substring(4));
		Assert.assertEquals("Jenkins: @juan te debe $9 @delucas", jenkins.escuchar(USUARIO + ": cuánto me debe @juan? @jenkins").substring(4));
		Assert.assertEquals("Jenkins: No le debes nada a @juan, @juan te debe $9 @delucas",
				jenkins.escuchar(USUARIO + ": cuánto le debo a @juan? @jenkins").substring(4));
	}

}