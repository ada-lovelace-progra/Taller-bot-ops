package vistas;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import plugins.Codificaciones;
import plugins.Youtube2;
import plugins.Zumbido;
import usuariosYAsistente.Usuario;
import javax.swing.JList;
import java.awt.Color;
import java.awt.Dimension;

public class PestanaPrueba {

	private Font fuente = new Font("Tahoma", Font.PLAIN, 11);
	private Usuario usuario;

	public PestanaPrueba(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel nuevo(int codChat) {
		JPanel panel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 394, 0 };
		gbl_panel.rowHeights = new int[] { 200, 28, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;

		JList<Object> mensajes = new JList<>();
		mensajes.setBackground(Color.WHITE);
		mensajes.setFont(fuente);
		panel.add(mensajes, gbc_scrollPane);

		JScrollPane scrollEnviar = new JScrollPane();
		GridBagConstraints gbc_scrollEnviar = new GridBagConstraints();
		gbc_scrollEnviar.fill = GridBagConstraints.BOTH;
		gbc_scrollEnviar.gridx = 0;
		gbc_scrollEnviar.gridy = 1;
		scrollEnviar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollEnviar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel.add(scrollEnviar, gbc_scrollEnviar);

		JEditorPane textEnviar = new JEditorPane();
		textEnviar.setContentType("text/plain");
		textEnviar.setFont(fuente);
		scrollEnviar.setViewportView(textEnviar);

		setearEventos(codChat, textEnviar, mensajes);
		cargaMensajesNuevosHilo(codChat, mensajes);

		agregarMensajeSimple(mensajes, "asdasjdhasjkd");
		agregarMensajeSimple(mensajes, "asdasjdhasjkd");
		agregarMensajeSimple(mensajes, "asdasjdhasjkd");
		agregarMensajeSimple(mensajes, "asdasjdhasjkd");

		return panel;
	}

	private void setearEventos(int codChat, JEditorPane textEnviar, JList<Object> mensajes) {

		textEnviar.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == '\n') {
					enviarMensaje(textEnviar, mensajes, codChat);
					textEnviar.setText("");
				} else if (textEnviar.getText().length() > 2) {
					char ch = textEnviar.getText().charAt(0);
					if (ch == '\n' || ch == '\r')
						textEnviar.setText(textEnviar.getText().substring(1));
				}
			}
		});

		textEnviar.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				textEnviar.setCaretPosition(textEnviar.getDocument().getLength());
			}

		});
	}

	private void eventoLink(JTextPane mensajes) {
		mensajes.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent hLinkEv) {
				String url = null;
				URL uURL = hLinkEv.getURL();
				if (uURL != null)
					url = uURL.toString();
				else
					url = hLinkEv.getDescription();

				if (hLinkEv.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					System.out.println("url: " + url);
					/*
					 * SimpleAttributeSet at =
					 * (SimpleAttributeSet)hLinkEv.getSourceElement().getAttributes().getAttribute(
					 * HTML.Tag.A); String title =
					 * at!=null?(String)at.getAttribute(HTML.Attribute.TITLE):null;
					 * if(title.contains("Ampliar")) //es un meme { Ampliar a = new Ampliar(url);
					 * a.setLocationRelativeTo(null); a.setVisible(true); } else
					 */if (Desktop.isDesktopSupported())
						try {
							Desktop.getDesktop().browse(new URI(url));
							;
						} catch (Exception e1) {
							System.out.println("fallo Desktop");
						}
					else
						try {
							new ProcessBuilder(url).start();
						} catch (Exception e2) {
							System.out.println("fallto tmabien forma fea...");
							try {
								String comando = url;
								Runtime.getRuntime().exec("start ");
								Runtime.getRuntime().exec(comando);
							} catch (Exception e3) {
								System.out.println("se acabo todo... todillo");
							}
						}
				} // fin if activated
			}
		});
	}

	private void cargaMensajesNuevosHilo(int codChat, JList<Object> mensajes) {
		new Thread() {
			public void run() {
				try {
					while (true) {
						String recibido = usuario.recibir(codChat);
						if (recibido.contains(":zumbido:"))
							new Zumbido().start();
						agregarMensajeSimple(mensajes, recibido);
						if (recibido.contains("youtube")) {
							System.out.println("youtubeee");
							mensajes.add(Youtube2.metodoLoco(true));
						}
					}
				} catch (Exception e) {
					// System.out.println("error recibiendo el mensaje");
				}
			}
		}.start();
	}

	private void agregarMensajeSimple(JList<Object> mensajes, String recibido) {
		JEditorPane nuevo = new JEditorPane();
		nuevo.setSize(new Dimension(100	, 20));
		
		nuevo.setContentType("text/html");
		String cadena = "<HTML><HEAD></HEAD><BODY><p>";
		cadena += recibido;
		cadena += "</p></BODY></HTML>";

		nuevo.setText(cadena);
		mensajes.add(nuevo);
	}

	private void enviarMensaje(JEditorPane textEnviar, JList<Object> mensajes, int codChat) {
		String mensaje = textEnviar.getText().trim();
		if (mensaje.length() > 0 && !mensaje.equals("\n")) {
			textEnviar.setText("");
			mensaje = Codificaciones.codificar(mensaje);
			try {
				usuario.enviar(codChat, mensaje);
				agregarMensajeSimple(mensajes, mensaje);
			} catch (Exception e) {
			}
		}
	}

}
