package vistas;

import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JTabbedPane;
import java.awt.TextArea;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import usuariosYAsistente.Usuario;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Cliente extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private Usuario user;
	private String Usuario;
	private static Cliente ventana;
	private List Conectados;
	private JPasswordField iniPass;
	private JTextField regEmail;
	private JPasswordField regPass;
	private JTextField regUser;
	private HTMLEditorKit editorKit;

	private String text;
	private JLayeredPane InisioSesion;
	private JLayeredPane Chat;
	private JLabel lblNewLabel;
	private JPanel panel;
	private JButton btnNewButton;
	private JTextPane mensajes;
	private TextArea aEnviar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventana = new Cliente();
					ventana.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Cliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 300);
		setAllBounds();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		iniciarSesion();
	}

	private void iniciarSesion() {
		InisioSesion = new JLayeredPane();
		contentPane.add(InisioSesion);
		InisioSesion.setLayout(null);

		iniPass = new JPasswordField();
		InisioSesion.setLayer(iniPass, 0);

		InisioSesion.add(iniPass);

		JTextField iniEmail = new JTextField();
		iniEmail.setBounds(11, 24, 143, 20);
		InisioSesion.add(iniEmail);
		iniEmail.setColumns(10);

		JLabel lblUsuario = new JLabel("Email");
		lblUsuario.setBounds(10, 11, 46, 14);
		InisioSesion.add(lblUsuario);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(11, 56, 80, 14);
		InisioSesion.add(lblContrasea);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(251, 0, 9, 261);
		InisioSesion.add(separator);

		regEmail = new JTextField();
		regEmail.setColumns(10);
		regEmail.setBounds(260, 24, 143, 20);
		InisioSesion.add(regEmail);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(259, 11, 46, 14);
		InisioSesion.add(lblEmail);

		JLabel lblUsuario_1 = new JLabel("Usuario");
		lblUsuario_1.setBounds(260, 56, 80, 14);
		InisioSesion.add(lblUsuario_1);

		JLabel label = new JLabel("Contrase\u00F1a");
		label.setBounds(260, 101, 80, 14);
		InisioSesion.add(label);

		regPass = new JPasswordField();
		regPass.setBounds(260, 115, 144, 20);
		InisioSesion.add(regPass);

		regUser = new JTextField();
		regUser.setColumns(10);
		regUser.setBounds(260, 70, 143, 20);
		InisioSesion.add(regUser);

		JButton iniciar = new JButton("Iniciar Sesion");
		iniciar.setBounds(11, 97, 117, 23);
		InisioSesion.add(iniciar);

		JButton registrar = new JButton("Registrarse e Iniciar Sesion");

		registrar.setBounds(260, 146, 173, 23);
		InisioSesion.add(registrar);
		InisioSesion.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { iniEmail, iniPass, iniciar, regEmail, regUser, regPass, registrar }));
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { iniEmail, iniPass, iniciar, regEmail, regUser, label, registrar }));

		registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sesionIniciada();
				} catch (Exception e) {
				}
			}
		});
	}

	private int XTot = 0, YTot = 0;

	private void setAllBounds() {
		new Thread() {
			public void run() {
				while (true) {
					int x = getWidth();
					int y = getHeight();
					if (XTot == 0)
						XTot = x;
					if (YTot == 0)
						YTot = y;
					double YRelacion = (double) y / (double) YTot, XRelacion = (double) x / (double) XTot;

					if (InisioSesion != null)
						InisioSesion.setBounds((int) (0 * XRelacion), (int) (0 * YRelacion), (int) (552 * XRelacion),
								(int) (261 * YRelacion));
					if (Chat != null)
						Chat.setBounds((int) (0 * XRelacion), (int) (0 * YRelacion), (int) (552 * XRelacion),
								(int) (261 * YRelacion));
					if (lblNewLabel != null)
						lblNewLabel.setBounds((int) (10 * XRelacion), (int) (0 * YRelacion), (int) (81 * XRelacion),
								(int) (24 * YRelacion));
					if (tabbedPane != null)
						tabbedPane.setBounds((int) (100 * XRelacion), (int) (0 * YRelacion), (int) (460 * XRelacion),
								(int) (261 * YRelacion));
					if (btnNewButton != null)
						btnNewButton.setBounds((int) (404 * XRelacion), (int) (165 * YRelacion), (int) (41 * XRelacion),
								(int) (68 * YRelacion));
					if (mensajes != null)
						mensajes.setBounds((int) (0 * XRelacion), (int) (0 * YRelacion), (int) (445 * XRelacion),
								(int) (160 * YRelacion));
					if (aEnviar != null)
						aEnviar.setBounds((int) (0 * XRelacion), (int) (165 * YRelacion), (int) (398 * XRelacion),
								(int) (68 * YRelacion));
					if (Conectados != null)
						Conectados.setBounds((int) (10 * XRelacion), (int) (26 * YRelacion), (int) (81 * XRelacion),
								(int) (224 * YRelacion));

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}
			}
		}.start();
	}

	private void sesionIniciada() throws Exception {
		Usuario = regUser.getText();
		ventana.setTitle(Usuario);
		user = new Usuario(Usuario);
		user.nuevoChat(0);
		user.enviar(0, "refresh");

		contentPane.removeAll();

		Chat = new JLayeredPane();

		contentPane.add(Chat);

		Conectados = new List();
		Conectados.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int cant = Conectados.getSelectedItems().length;
				if (cant == 1) {
					try {
						int codChat = user.pedirNuevoChat(Conectados.getSelectedItem());
						if (codChat != -1)
							nuevaPesatana(codChat + "", codChat);
					} catch (Exception e1) {
					}
					Conectados.deselect(Conectados.getSelectedIndex());
				}
			}
		});
		Chat.add(Conectados);

		new cargaDeConectados().start();

		lblNewLabel = new JLabel("Conectados");
		Chat.add(lblNewLabel);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		Chat.add(tabbedPane);
	}

	private void enviarMensaje(TextArea aEnviar, JEditorPane mensajes, int codChat) {
		String mensaje = aEnviar.getText();
		if (mensaje.length() > 0 && !mensaje.equals("\r\n")) {
			aEnviar.setText("");
			if (esImagen(mensaje)) {
				mensaje = codificarImagen(mensaje);
			}
			mensaje = codificarYoutube(mensaje);
			HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
			try {
				editorKit.insertHTML(doc, doc.getLength(), Usuario + ": " + mensaje, 0, 0, null);
				user.enviar(codChat, mensaje);
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

	@SuppressWarnings("unused")
	private String codificarYoutube2(String recibido) {

		// https://stackoverflow.com/questions/21385904/how-to-insert-an-iframe-a-youtube-video-inside-a-jeditorpane

		String ini = "<iframe width=\"560\" height=\"315\" src=\"";
		String fin = "\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>";

		Matcher asd = Pattern.compile("(www\\S+)").matcher(recibido);
		String link = "";
		if (asd.find()) {
			link = asd.group(1);
			return recibido.replace(link, ini + link + fin);
		}
		asd = Pattern.compile("(http\\S+)").matcher(recibido);
		if (asd.find()) {
			link = asd.group(1);
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
			return recibido.replace(link, "<img src=\"" + link + "\">");
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

	class zumbido extends Thread {
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

	private void nuevaPesatana(String nombreTab, int codChat) throws UnknownHostException, Exception {
		panel = new JPanel();
		panel.setLayout(null);
		btnNewButton = new JButton("Enviar");
		panel.add(btnNewButton);

		mensajes = new JTextPane();
		mensajes.setEditable(false);
		panel.add(mensajes);

		aEnviar = new TextArea();
		aEnviar.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == '\n')
					enviarMensaje(aEnviar, mensajes, codChat);
				else if (aEnviar.getText().startsWith("\r\n"))
					aEnviar.setText(aEnviar.getText().substring(2));
			}
		});

		panel.add(aEnviar);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enviarMensaje(aEnviar, mensajes, codChat);
			}
		});

		new Thread() {

			public void run() {
				cargaContenidoDeChat(mensajes);
				try {
					while (true) {
						String recibido = user.recibir(codChat);
						if (recibido.contains(":zumbido:"))
							new zumbido().start();
						HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
						editorKit.insertHTML(doc, doc.getLength(), recibido, 0, 0, null);
					}
				} catch (Exception e) {
					System.out.println("error recibiendo el mensaje");
				}
			}

			private void cargaContenidoDeChat(JTextPane textPane) {
				try {
					text = "<HTML>\r\n" + "<HEAD>\r\n" + "</HEAD>\r\n" + "<BODY>\r\n" + "</BODY>\r\n" + "</HTML>";
					textPane.setContentType("text/html");
					textPane.setEditable(true);
					HTMLDocument doc = (HTMLDocument) textPane.getDocument();
					editorKit = (HTMLEditorKit) textPane.getEditorKit();
					editorKit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
				} catch (Exception e) {
				}
			}

		}.start();

		tabbedPane.addTab(nombreTab, null, panel, null);
	}

	class cargaDeConectados extends Thread {
		public void run() {
			try {
				String anterior = null;
				while (true) {
					String nuevo = user.recibir(0);
					if (nuevo.contains("?")) {
						if (!nuevo.equals(anterior)) {
							Conectados.removeAll();
							for (String user : nuevo.split("\\?"))
								if (!user.equals(Usuario))
									Conectados.add(user);
							anterior = nuevo;
						}
					} else
						nuevaPesatana("Pestana Pedida", Integer.parseInt(nuevo.substring(16)));

				}
			} catch (Exception e) {
			}
		}
	}

}
