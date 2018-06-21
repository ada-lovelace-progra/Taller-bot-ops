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
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import usuariosYAsistente.Usuario;

public class Pestana {
	private Usuario usuario;

	public Pestana(Usuario usuario) {
		this.usuario = usuario;
	}

	public JPanel nuevo(int codChat) {
		JTextArea textEnviar = new JTextArea();
		// textEnviar.setMinimumSize(new Dimension(20, 22));
		textEnviar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textEnviar.setLineWrap(true);

		JScrollPane scrollChiquito = new JScrollPane(textEnviar);
		scrollChiquito.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollChiquito.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollChiquito = new GridBagConstraints();
		gbc_scrollChiquito.fill = GridBagConstraints.BOTH;
		gbc_scrollChiquito.gridx = 1;
		gbc_scrollChiquito.gridy = 2;

		Font fuente = new Font("Tahoma", Font.PLAIN, 11);
		JEditorPane mensajes = new JEditorPane();
		mensajes.setEditable(false);
		mensajes.setContentType("text/html");
		mensajes.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
		mensajes.setFont(fuente);

		setearEventos(codChat, textEnviar, mensajes);

		JScrollPane scrollPane = new JScrollPane(mensajes);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		cargaMensajesNuevosHilo(codChat, mensajes);

		JPanel pane = new JPanel();

		pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 29, 229, 25, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 2.0, 0.0, Double.MIN_VALUE };
		pane.setLayout(gridBagLayout);
		
		pane.setLayout(new GridBagLayout());
		pane.add(scrollPane, gridBagLayout);
		pane.add(scrollChiquito, gbc_scrollChiquito);

		return pane;
	}

	private void setearEventos(int codChat, JTextArea textEnviar, JEditorPane mensajes) {
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
					if (Desktop.isDesktopSupported())
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
				} else if (textEnviar.getText().startsWith("\n"))
					textEnviar.setText(textEnviar.getText().substring(1));
			}
		});
	}

	private void cargaMensajesNuevosHilo(int codChat, JEditorPane mensajes) {
		new Thread() {
			public void run() {
				cargaContenidoDeChat();
				try {
					while (true) {
						String recibido = usuario.recibir(codChat);
						if (recibido.contains(":zumbido:"))
							new Zumbido().start();
						HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
						HTMLEditorKit editorKit = (HTMLEditorKit) mensajes.getEditorKit();
						editorKit.insertHTML(doc, doc.getLength(), recibido, 0, 0, null);
						mensajes.setCaretPosition(doc.getLength());
					}
				} catch (Exception e) {
					System.out.println("error recibiendo el mensaje");
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

	private void enviarMensaje(JTextArea aEnviar, JEditorPane mensajes, int codChat) {
		String mensaje = aEnviar.getText();
		if (mensaje.length() > 0 && !mensaje.equals("\n")) {
			aEnviar.setText("");
			mensaje = Codificaciones.codificar(mensaje);
			HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
			try {
				HTMLEditorKit editorKit = (HTMLEditorKit) mensajes.getEditorKit();
				editorKit.insertHTML(doc, doc.getLength(), usuario.nombre + ": " + mensaje, 0, 0, null);
				usuario.enviar(codChat, mensaje);
				mensajes.setCaretPosition(mensajes.getDocument().getLength());
			} catch (Exception e) {
			}
		}
	}

}
