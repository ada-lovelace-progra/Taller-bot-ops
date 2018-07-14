package vistas;

import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import tiposDeMensaje.TextoHtml;
import tiposDeMensaje.TextoPlano;
import tiposDeMensaje.Youtube;

public class Mensaje extends JPanel {
	/**
	 * 
	 */
	private static String regexHTML = ".*<.*http.*>.*";

	private static final long serialVersionUID = 1L;

	public static GridBagConstraints gbc() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		return gbc;
	}

	public Component nuevo(String mensaje) {
		if (mensaje.toLowerCase().contains("youtube"))
			return new Youtube("https://www.youtube.com/embed/DLzxrzFCyOs?autoplay=1");
		if (mensaje.matches(regexHTML))
			return new TextoHtml(mensaje);
		return new TextoPlano(mensaje);
	}
}