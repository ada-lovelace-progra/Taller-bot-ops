package vistas;

import java.awt.EventQueue;
import java.awt.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import usuariosYAsistente.*;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import java.awt.Desktop;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.MouseEvent;

public class Chat extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private static Chat ventana;
	private int codChatAbrir = -1;
	private HTMLEditorKit editorKit;
	private String text;
	private List listaConectados;
	private JTabbedPane tabChats;
	private JTextArea textEnviar;
	private JEditorPane mensajes;
	private JScrollPane scrollPane;
	private JScrollPane scrollChiquito;
	private Font fuente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventana = new Chat(null);
					ventana.setLocationRelativeTo(null);
					ventana.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public Chat(String user) throws Exception {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setMinimumSize(new Dimension(542, 346));
		usuario = new Usuario(user);
		ventana = this;
		ventana.setTitle(user);
		usuario.nuevoChat(0);
		usuario.enviar(0, "refresh");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 29, 229, 25, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 2.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gridBagLayout);

		JLabel lblConectados = new JLabel("Conectados");
		GridBagConstraints gbc_lblConectados = new GridBagConstraints();
		gbc_lblConectados.insets = new Insets(0, 0, 5, 5);
		gbc_lblConectados.gridx = 0;
		gbc_lblConectados.gridy = 0;
		getContentPane().add(lblConectados, gbc_lblConectados);

		listaConectados = new List();
		lblConectados.setLabelFor(listaConectados);
		GridBagConstraints gbc_listaConectados = new GridBagConstraints();
		gbc_listaConectados.gridheight = 2;
		gbc_listaConectados.insets = new Insets(0, 0, 0, 5);
		gbc_listaConectados.fill = GridBagConstraints.BOTH;
		gbc_listaConectados.gridx = 0;
		gbc_listaConectados.gridy = 1;
		getContentPane().add(listaConectados, gbc_listaConectados);

		tabChats = new JTabbedPane(JTabbedPane.TOP);
		tabChats.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.control));
		tabChats.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tabChats.setSize(this.getSize());
		GridBagConstraints gbc_tabChats = new GridBagConstraints();
		gbc_tabChats.insets = new Insets(0, 0, 5, 0);
		gbc_tabChats.fill = GridBagConstraints.BOTH;
		gbc_tabChats.gridx = 1;
		gbc_tabChats.gridy = 1;

		getContentPane().add(tabChats, gbc_tabChats);

		new CargaDeConectados().start();

		listaConectados.addMouseListener(new MouseAdapter() {
			private String usuariosSeleccionados = "";

			public void mouseClicked(MouseEvent e) {
				int cant = listaConectados.getSelectedItems().length;
				String selectedItem = listaConectados.getSelectedItem();
				if (cant == 1 && !selectedItem.equals(user) && !usuariosSeleccionados.contains(selectedItem)) {
					try {
						usuariosSeleccionados += selectedItem + " ";
						int codChat = codChatAbrir = usuario.pedirNuevoChat(selectedItem);
						if (codChat != -1) // ver que ya no tenga la pestaña abierta
							nuevaTab(codChat + "", codChat);
					} catch (Exception e1) {
					}
				}
				listaConectados.deselect(listaConectados.getSelectedIndex());
			}
		});

	}

	private void nuevaTab(String nombre, int codChat) {
		if (tabChats.indexOfTab(nombre) != -1) // ya esta la tab abierta: de ultima que le haga focus
		{
			tabChats.setSelectedIndex(tabChats.indexOfTab(nombre));
		}

		textEnviar = new JTextArea();
		// textEnviar.setMinimumSize(new Dimension(20, 22));
		textEnviar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textEnviar.setLineWrap(true);

		scrollChiquito = new JScrollPane(textEnviar);
		scrollChiquito.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollChiquito.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollChiquito = new GridBagConstraints();
		gbc_scrollChiquito.fill = GridBagConstraints.BOTH;
		gbc_scrollChiquito.gridx = 1;
		gbc_scrollChiquito.gridy = 2;
		getContentPane().add(scrollChiquito, gbc_scrollChiquito);

		mensajes = new JEditorPane();
		mensajes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mensajes.setEditable(false);
		mensajes.setContentType("text/html");
		// mensajes.setPreferredSize(this.getSize());
		fuente = new Font("Tahoma", Font.PLAIN, 11);
		mensajes.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
		mensajes.setFont(fuente);

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

		scrollPane = new JScrollPane(mensajes);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		mensajes.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

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
				} else if (textEnviar.getText().startsWith("\r\n"))
					textEnviar.setText(textEnviar.getText().substring(2));
			}
		});

		// falta el boton de enviar

		new Thread() {
			public void run() {
				cargaContenidoDeChat(mensajes);
				try {
					while (true) {
						String recibido = usuario.recibir(codChat);
						if (recibido.contains(":zumbido:"))
							new Zumbido().start();
						HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
						editorKit.insertHTML(doc, doc.getLength(), recibido, 0, 0, null);
						mensajes.setCaretPosition(doc.getLength());
					}
				} catch (Exception e) {
					System.out.println("error recibiendo el mensaje");
				}
			}

			private void cargaContenidoDeChat(JEditorPane textPane) {
				try {
					text = "<HTML>\r\n" + "<HEAD>\r\n" + "</HEAD>\r\n" + "<BODY>\r\n" + "</BODY>\r\n" + "</HTML>";
					textPane.setContentType("text/html");
					HTMLDocument doc = (HTMLDocument) textPane.getDocument();
					editorKit = (HTMLEditorKit) textPane.getEditorKit();
					editorKit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
					// JScrollBar vertical = scrollPane.getVerticalScrollBar();
					textPane.setCaretPosition(textPane.getDocument().getLength());
				} catch (Exception e) {
				}
			}
		}.start();

		tabChats.addTab(nombre, null, scrollPane, null);
	}

	private void enviarMensaje(JTextArea aEnviar, JEditorPane mensajes, int codChat) {
		String mensaje = aEnviar.getText();
		if (mensaje.length() > 0 && !mensaje.equals("\r\n")) {
			aEnviar.setText("");
			if (esImagen(mensaje))
				mensaje = codificarImagen(mensaje);
			else if (mensaje.matches(".*;.*;.*"))
				mensaje = codificarYoutube(mensaje);
			else if (esLink(mensaje)) {
				mensaje = codificarLink(mensaje);
			}
			HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
			try {
				editorKit.insertHTML(doc, doc.getLength(), usuario.nombre + ": " + mensaje, 0, 0, null);
				usuario.enviar(codChat, mensaje);
				// JScrollBar vertical = scrollPane.getVerticalScrollBar();
				mensajes.setCaretPosition(mensajes.getDocument().getLength());
				// vertical.setValue(vertical.getMaximum()+1);
			} catch (Exception e) {
			}
		}
	}

	private String codificarYoutube(String recibido) {
		Matcher asd = Pattern.compile(";(\\S+);").matcher(recibido);
		String link = "";
		if (asd.find()) {
			link = asd.group(1);
			return recibido.replace(";" + link + ";",
					"<a href=\"www.youtube.com/watch?v=dQw4w9WgXcQ\">" + link + "</a>");
		}
		return recibido;
	}

	private String codificarLink(String recibido) {
		String ini = "<a href=\"";
		String fin = "\">";
		Matcher asd = Pattern.compile("(http\\S+)").matcher(recibido);
		String link = "";
		if (asd.find()) {
			link = asd.group(1);
			// return recibido.replace(link, ini + link + fin + link);
			return recibido.replace(link, ini + link + fin);
			// return recibido.replace(link,obtenerTituloYVistaPrevia(link));
		}
		asd = Pattern.compile("(www\\S+)").matcher(recibido);
		if (asd.find()) {
			link = asd.group(1);
			// return recibido.replace(link, ini + link + fin + link);
			return recibido.replace(link, ini + link + fin);
		}
		asd = Pattern.compile("(\\S+.\\S+)").matcher(recibido);
		if (asd.find()) {
			link = asd.group(1);
			// return recibido.replace(link, ini + "www." + link + fin + link);
			return recibido.replace(link, ini + link + fin);
		}
		return recibido;
	}

	@SuppressWarnings("unused")
	private boolean esYoutube2(String recibido) {
		return recibido.contains("youtu");
	}

	private String codificarImagen(String recibido) {
		Matcher asd = Pattern.compile("(www\\S+)").matcher(recibido);
		String link = "";
		if (asd.find()) {
			link = asd.group(1);
			return recibido.replace(link, "<img width=\"100\" height=\"50\" src=\"" + link + "\">");
		}
		asd = Pattern.compile("(http\\S+)").matcher(recibido);
		if (asd.find()) {
			link = asd.group(1);
			return recibido.replace(link, "<img width=\"100\" height=\"50\" src=\"" + link + "\">");
		}
		return recibido;
	}

	private boolean esImagen(String recibido) {
		if (esLink(recibido))
			return recibido.contains("jpg") || recibido.contains("gif") || recibido.contains("png")
					|| recibido.contains("img") || comprobarQueEsImagenDeFormaFea(recibido);
		else
			return false;
	}

	private boolean comprobarQueEsImagenDeFormaFea(String recibido) {
		Matcher asd = Pattern.compile("(www\\S+)").matcher(recibido);
		String link = "";
		if (asd.find()) {
			link = asd.group(1);
			try {
				ImageIO.read(new URL(link));
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	private boolean esLink(String recibido) {
		return recibido.contains("http") || recibido.contains("https") || recibido.contains("www")
				|| recibido.contains(".com");
	}

	class CargaDeConectados extends Thread {
		public void run() {
			try {
				String anterior = null;
				while (true) {
					String nuevo = usuario.recibir(0);
					if (nuevo.contains("?")) {
						if (!nuevo.equals(anterior)) {
							listaConectados.removeAll();
							for (String user : nuevo.split("\\?"))
								if (!user.equals(usuario.nombre))
									listaConectados.add(user);
							anterior = nuevo;
						}
					} else {
						// nuevaTab("Pestana Pedida", Integer.parseInt(nuevo.substring(16)));
						if (tabChats.indexOfTab(codChatAbrir + "") != -1) // ya esta la tab abierta: de ultima que le
																			// haga focus
						{
							tabChats.setSelectedIndex(tabChats.indexOfTab(codChatAbrir + ""));
						} else {
							nuevaTab("cargacontNO", Integer.parseInt(nuevo.substring(16)));
						}
					}
				}
			} catch (Exception e) {
			}
		}
	}

	class Zumbido extends Thread {
		public void run() {
			System.out.println("iniciado vibrado");
			int veces = 50;
			int x = (int) ventana.getLocation().getY();
			int y = (int) ventana.getLocation().getX();
			while (veces-- != 0) {
				int x1 = (int) (Math.random() * 20 - 10);
				int y1 = (int) (Math.random() * 20 - 10);
				ventana.setLocation(x + x1, y + y1);
				try {
					Thread.sleep(50);
				} catch (Exception e) {
					System.out.println("error 'vibrando'");
				}
			}
			ventana.setLocation(x, y);
		}
	}
}
