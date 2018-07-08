package vistas;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import plugins.Codificaciones;
import plugins.Youtube2;
import plugins.Zumbido;
import usuariosYAsistente.Usuario;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Pestana {

	private Font fuente = new Font("Tahoma", Font.PLAIN, 11);
	private Usuario usuario;
	private boolean setearonNombre = false;
	private String nombrePestana;
	private JTabbedPane tabChats;
	private int indicePestana;
	private char privacidad;
	private boolean setearonPrivacidad;
	private int mensajesSinLeer = 0;
	private Chat ventana;
	private static final Color[] colorArray = { Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA,
			Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW };

	public Pestana(Usuario usuario, JTabbedPane tabChats, Chat ventana) {
		this.usuario = usuario;
		this.tabChats = tabChats;
		indicePestana = tabChats.getTabCount();
		this.ventana = ventana;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel nuevo(int codChat) {
		JPanel panel = new JPanel();
		panel.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				mensajesSinLeer = 0;
			}
		});
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 394, 0 };
		gbl_panel.rowHeights = new int[] { 200, 28, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);

		JEditorPane mensajes = new JEditorPane();
		mensajes.setContentType("text/html");
		mensajes.setEditable(false);
		mensajes.setFont(fuente);
		scrollPane.setViewportView(mensajes);

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

		return panel;
	}

	private void setearEventos(int codChat, JEditorPane textEnviar, JEditorPane mensajes) {
		mensajes.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent hLinkEv) {
				String url = null;
				URL uURL = hLinkEv.getURL();
				if (uURL != null)
					url = uURL.toString();
				else
					url = hLinkEv.getDescription();

				if (hLinkEv.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					if (Desktop.isDesktopSupported())
						try {
							Desktop.getDesktop().browse(new URI(url));
						} catch (Exception e1) {
						}
					else
						try {
							new ProcessBuilder(url).start();
						} catch (Exception e2) {
							try {
								String comando = url;
								Runtime.getRuntime().exec("start ");
								Runtime.getRuntime().exec(comando);
							} catch (Exception e3) {
							}
						}
				}
			}
		});

		mensajes.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				mensajes.setCaretPosition(mensajes.getDocument().getLength());
			}

		});

		textEnviar.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == '\n') {
					enviarMensaje(textEnviar, mensajes, codChat);
					textEnviar.setText("");
				} else if (textEnviar.getText().length() > 1) {
					char charAt = textEnviar.getText().charAt(0);
					try {
						while (charAt == '\n' || charAt == '\r' && textEnviar.getText().length() > 1) {
							textEnviar.setText(textEnviar.getText().substring(1));
							charAt = textEnviar.getText().charAt(0);
						}
					} catch (Exception e) {
					}
					try {
						int length = textEnviar.getText().length();
						charAt = textEnviar.getText().charAt(length - 1);
						while (charAt == '\n' || charAt == '\r' && textEnviar.getText().length() > 1) {
							textEnviar.setText(textEnviar.getText().substring(0, --length - 1));
							charAt = textEnviar.getText().charAt(length - 1);
						}
					} catch (Exception e) {
					}
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

		mensajes.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0) {
				mensajesSinLeer = 0;
			}
		});

		textEnviar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				mensajesSinLeer = 0;
			}
		});
	}

	private void cargaMensajesNuevosHilo(int codChat, JEditorPane mensajes) {
		new Thread() {
			public void run() {
				cargaContenidoDeChat();
				while (true) {
					try {
						String recibido = usuario.recibir(codChat);

						zumbido(recibido);

						if (setearonNombre && setearonPrivacidad && esInvitacion(recibido))
							recibido = codificarInvitacion(recibido);

						if (estanSeteandoElTitulo(recibido))
							setearTitulo(recibido);

						if (estanSeteandoPrivacidad(recibido))
							setearPrivacidad(recibido, codChat);

						// youtube(mensajes, recibido);

						cargarMensaje(mensajes, codChat, recibido);
						mensajesSinLeer++;
					} catch (Exception e) {
					}
				}
			}

			private void zumbido(String recibido) {
				if (recibido.contains(":zumbido:"))
					new Zumbido(ventana).start();
			}

			@SuppressWarnings("unused")
			private void youtube(JEditorPane mensajes, String recibido) {
				if (recibido.contains("youtube")) {
					mensajes.add(Youtube2.metodoLoco(true));
				}
			}

			private void cargaContenidoDeChat() {
				try {
					mensajes.setContentType("text/html");
					HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
					HTMLEditorKit editorKit = (HTMLEditorKit) mensajes.getEditorKit();
					editorKit.insertHTML(doc, doc.getLength(),
							"<HTML>\r\n" + "<HEAD>\r\n" + "</HEAD>\r\n" + "<BODY>\r\n" + "</BODY>\r\n" + "</HTML>", 0,
							0, null);
					// JScrollBar vertical = scrollPane.getVerticalScrollBar();
					mensajes.setCaretPosition(mensajes.getDocument().getLength());
				} catch (Exception e) {
				}
			}
		}.start();
	}

	private void enviarMensaje(JEditorPane textEnviar, JEditorPane mensajes, int codChat) {
		String mensaje = textEnviar.getText().trim();
		if (mensaje.length() > 0 && !mensaje.equals("\n")) {
			textEnviar.setText("");
			mensaje = Codificaciones.codificar(mensaje);

			if (setearonPrivacidad && esInvitacion(mensaje))
				mensaje = codificarInvitacion(mensaje);

			if (estanSeteandoElTitulo(mensaje))
				setearTitulo(mensaje);

			if (setearonNombre && estanSeteandoPrivacidad(mensaje))
				setearPrivacidad(mensaje, codChat);

			cargarMensaje(mensajes, codChat, usuario.nombre + ": " + mensaje);

			try {
				usuario.enviar(codChat, mensaje);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean fueSeteado = false;

	private void cargarMensaje(JEditorPane mensajes, int codChat, String mensaje) {
		if (!fueSeteado) {
			fueSeteado = true;
			nombrePestana = tabChats.getTitleAt(indicePestana);
			setearNombreSala.start();
		}

		if (!mensaje.matches("^(.*: )?#.=\\S+$")) {
			if (mensaje.contains("@") && mensaje.contains("#"))
				mensaje = mensaje.substring(0, mensaje.indexOf("#"));
			try {
				HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
				HTMLEditorKit editorKit = (HTMLEditorKit) mensajes.getEditorKit();
				editorKit.insertHTML(doc, doc.getLength(), mensaje, 0, 0, null);
				mensajes.setCaretPosition(mensajes.getDocument().getLength());
			} catch (Exception e) {
			}
		}
	}

	private void setearTitulo(String mensaje) {
		Matcher regex = Pattern.compile("#T=(.*)\\.?").matcher(mensaje);
		if (regex.find()) {
			setearonNombre = true;
			nombrePestana = regex.group(1);
			tabChats.setTitleAt(indicePestana, "#" + nombrePestana);
		}
	}

	private boolean estanSeteandoPrivacidad(String mensaje) {
		return mensaje.contains("#P=");
	}

	private void setearPrivacidad(String mensaje, int codChat) {
		Matcher regex = Pattern.compile("#P=(.)").matcher(mensaje);
		if (regex.find()) {
			setearonPrivacidad = true;
			privacidad = regex.group(1).charAt(0);

			// decirle al server que agregue la sala en caso de ser publica

			try {
				usuario.enviar(0, "agregarSala" + tabChats.getTitleAt(indicePestana) + privacidad
						+ String.format("%04d", codChat));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private boolean estanSeteandoElTitulo(String mensaje) {
		return mensaje.contains("#T=");
	}

	private String codificarInvitacion(String mensaje) {
		return mensaje + tabChats.getTitleAt(indicePestana) + privacidad;
	}

	private boolean esInvitacion(String mensaje) {
		return mensaje.contains("@");
	}

	private final Thread setearNombreSala = new Thread() {

		public void run() {
			Color colorNotificacion = colorArray[(int) (Math.random() * 8)];
			Color color = tabChats.getBackground();
			boolean sinLeer = true;
			while (true)
				try {
					if (mensajesSinLeer != 0) {
						if (sinLeer) {
							tabChats.setBackgroundAt(indicePestana, colorNotificacion);
							sinLeer = false;
						}
						String nombre = nombrePestana + " (" + mensajesSinLeer + ")";
						tabChats.setTitleAt(indicePestana, nombre);
						Thread.sleep(500);
						tabChats.setTitleAt(indicePestana, nombrePestana + "....");
					} else if (!sinLeer) {
						sinLeer = true;
						tabChats.setBackgroundAt(indicePestana, color);
						tabChats.setTitleAt(indicePestana, nombrePestana);
					}
					Thread.sleep(500);
				} catch (Exception e) {
				}
		}
	};

}
