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

	/**
	 * 
	 */
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

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public Cliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		iniciarSesion();
	}

	private void iniciarSesion() {
		JLayeredPane InisioSesion = new JLayeredPane();
		InisioSesion.setBounds(0, 0, 552, 261);
		contentPane.add(InisioSesion);
		InisioSesion.setLayout(null);

		iniPass = new JPasswordField();
		InisioSesion.setLayer(iniPass, 0);
		iniPass.setBounds(11, 70, 144, 20);
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

	private void sesionIniciada() throws Exception {
		Usuario = regUser.getText();
		ventana.setTitle(Usuario);
		user = new Usuario(Usuario);
		user.nuevoChat(0);
		user.enviar(0, "refresh");

		contentPane.removeAll();

		JLayeredPane Chat = new JLayeredPane();
		Chat.setBounds(0, 0, 552, 261);
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
		Conectados.setBounds(10, 26, 81, 224);
		Chat.add(Conectados);

		new cargaDeConectados().start();

		JLabel lblNewLabel = new JLabel("Conectados");
		lblNewLabel.setBounds(10, 0, 81, 24);
		Chat.add(lblNewLabel);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(100, 0, 460, 261);
		Chat.add(tabbedPane);
	}

	private void enviarMensaje(TextArea aEnviar, JEditorPane mensajes, int codChat) {
		String mensaje = aEnviar.getText();
		if (mensaje.length() > 0 && !mensaje.equals("\r\n")) {
			aEnviar.setText("");
			HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
			try {
				editorKit.insertHTML(doc, doc.getLength(), Usuario + ": " + mensaje, 0, 0, null);
				user.enviar(codChat, mensaje);
			} catch (Exception e) {
			}
		}
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
		JPanel panel = new JPanel();
		panel.setLayout(null);
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.setBounds(404, 165, 41, 68);
		panel.add(btnNewButton);

		JTextPane mensajes = new JTextPane();
		mensajes.setBounds(0, 0, 445, 160);
		mensajes.setEditable(false);
		panel.add(mensajes);

		TextArea aEnviar = new TextArea();
		aEnviar.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == '\n')
					enviarMensaje(aEnviar, mensajes, codChat);
				else if (aEnviar.getText().startsWith("\r\n"))
					aEnviar.setText(aEnviar.getText().substring(2));
			}
		});

		aEnviar.setBounds(0, 165, 398, 68);
		panel.add(aEnviar);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enviarMensaje(aEnviar, mensajes, codChat);
			}
		});

		new Thread() {

			public void run() {
				cargaContenidoDeChat(mensajes);
				while (true)
					try {
						String recibido = user.recibir(codChat);
						if (esImagen(recibido)) {
							recibido = codificarImagen(recibido);
						} else if (esYoutube(recibido)) {
							recibido = codificarYoutube(recibido);
						}
						if (recibido.contains(":zumbido:"))
							new zumbido().start();
						HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
						editorKit.insertHTML(doc, doc.getLength(), recibido, 0, 0, null);
					} catch (Exception e) {
						System.out.println("error recibiendo el mensaje");
					}
			}

			private String codificarYoutube(String recibido) {
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
				return null;
			}

			private boolean esYoutube(String recibido) {
				return recibido.contains("youtu");
			}

			private String codificarImagen(String recibido) {
				Matcher asd = Pattern.compile("(www\\S+)").matcher(recibido);
				String link = "";
				if (asd.find()) {
					link = asd.group(1);
					return recibido.replace(link, "<img src=\"" + link + "\">");
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
				}

				try {
					ImageIO.read(new URL(link));
				} catch (Exception e) {
					return false;
				}
				return true;
			}

			private boolean esLink(String recibido) {
				return recibido.contains("http") || recibido.contains("https") || recibido.contains("www")
						|| recibido.contains(".com");
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
