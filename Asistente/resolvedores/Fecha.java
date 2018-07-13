package resolvedores;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.*;

import armadores.RespuestaGenerico;
import fecha.FechaAhora;
import fecha.FechaCompleta;
import fecha.FechaDentroDe;
import fecha.FechaDesde;
import fecha.FechaDia;
import fecha.FechaGenerico;
import fecha.FechaGet;
import fecha.FechaHaceQue;
import fecha.FechaHasta;
import fecha.FechaHora;

/**
 * Resolvedor, devuelve operaciones relacionadas a la fecha.
 * 
 * dias hasta dias desde hora semana fecha actual
 */
public class Fecha extends RespuestaGenerico {
	protected Pattern patternFechaCompleta = Pattern.compile(".*([0-9][0-9])/([0-9]+)/([0-9]+).*");
	protected Pattern patternDias = Pattern.compile(".* ([0-9]+) ([a-z]+).*");
	protected DateTime fecha = null;
	protected Date fecha2 = null;
	private FechaGenerico primero;
	private String inicial = null;

	/**
	 * Se agrega un constructor para testear. recive s en formato dd/MM/aaaa y hace
	 * las cuentas en base a eso, asume que son las 10:30 de ese dia.
	 * 
	 * @param s
	 */
	@SuppressWarnings("deprecation")
	public Fecha(String s) {
		Matcher regexFechaCompleta = patternFechaCompleta.matcher(s);
		if (regexFechaCompleta.find()) {
			int dia = Integer.parseInt(regexFechaCompleta.group(1)),
					mes = Integer.parseInt(regexFechaCompleta.group(2)),
					ano = Integer.parseInt(regexFechaCompleta.group(3));
			this.fecha = new DateTime(ano, mes, dia, 10, 30, 0, 0);
			this.fecha2 = new Date(ano - 1900, mes - 1, dia, 10, 30);
		}
		this.inicial = s;
	}

	public Fecha() {
		super();

	}

	public void crearCadena() {
		if (inicial == null) {
			FechaGenerico ahora = new FechaAhora();
			FechaGenerico get = new FechaGet();
			FechaGenerico comp = new FechaCompleta();
			FechaGenerico hora = new FechaHora();
			FechaGenerico falta = new FechaHasta();
			FechaGenerico paso = new FechaDesde();
			FechaGenerico dentro = new FechaDentroDe();
			FechaGenerico hace = new FechaHaceQue();
			FechaGenerico dia = new FechaDia();

			ahora.siguiente(get);
			get.siguiente(comp);
			comp.siguiente(hora);
			hora.siguiente(falta);
			falta.siguiente(paso);
			paso.siguiente(dentro);
			dentro.siguiente(hace);
			hace.siguiente(dia);
			this.primero = ahora;
		} else {
			FechaGenerico ahora = new FechaAhora(this.inicial);
			FechaGenerico get = new FechaGet(this.inicial);
			FechaGenerico comp = new FechaCompleta(this.inicial);
			FechaGenerico hora = new FechaHora(this.inicial);
			FechaGenerico falta = new FechaHasta(this.inicial);
			FechaGenerico paso = new FechaDesde(this.inicial);
			FechaGenerico dentro = new FechaDentroDe(this.inicial);
			FechaGenerico hace = new FechaHaceQue(this.inicial);
			FechaGenerico dia = new FechaDia(this.inicial);

			ahora.siguiente(get);
			get.siguiente(comp);
			comp.siguiente(hora);
			hora.siguiente(falta);
			falta.siguiente(paso);
			paso.siguiente(dentro);
			dentro.siguiente(hace);
			hace.siguiente(dia);
			this.primero = ahora;
		}
	}

	@Override
	public String intentarResponder(String mensaje) {
		if(this.primero==null)
			crearCadena();
		return this.primero.request(mensaje);
	}

}
