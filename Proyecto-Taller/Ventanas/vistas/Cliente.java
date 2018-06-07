package vistas;

import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.TextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.UnknownHostException;
import javax.swing.JLabel;
import usuariosYAsistente.Usuario;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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
	Usuario user;
	String Usuario;
	private static Cliente ventana;
	private List Conectados;
	private JPasswordField iniPass;
	private JTextField regEmail;
	private JPasswordField regPass;
	private JTextField regUser;

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
					zumbido.start();
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
				}
			}
		});
		Conectados.setBounds(10, 26, 81, 224);
		Chat.add(Conectados);

		cargaDeConectados.start();

		JLabel lblNewLabel = new JLabel("Conectados");
		lblNewLabel.setBounds(10, 0, 81, 24);
		Chat.add(lblNewLabel);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(100, 0, 460, 261);
		Chat.add(tabbedPane);

		boolean a = false;
		while (a)
			while (!user.pila.isEmpty())
				nuevaPesatana("ni ideaa", Integer.parseInt(user.pila.pop()));
	}

	private Thread cargaDeConectados = new Thread() {

		public void run() {
			try {
				String anterior = null;
				while (true) {
					String nuevo = user.recibir(0);
					if (nuevo.contains("?"))
						if (!nuevo.equals(anterior)) {
							Conectados.removeAll();
							for (String user : nuevo.split("\\?"))
								Conectados.add(user);
							anterior = nuevo;
						}
				}
			} catch (Exception e) {
			}
		}
	};

	private void nuevaPesatana(String nombreTab, int codChat) throws UnknownHostException, Exception {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.setBounds(404, 165, 41, 68);
		panel.add(btnNewButton);

		TextArea mensajes = new TextArea();
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
				while (true)
					try {
						mensajes.append(user.recibir(codChat) + "\n");
					} catch (Exception e) {
					}
			}
		}.start();

		tabbedPane.addTab(nombreTab, null, panel, null);
	}

	private void enviarMensaje(TextArea aEnviar, TextArea mensajes, int codChat) {
		String mensaje = aEnviar.getText();
		if (mensaje.length() > 0 && !mensaje.equals("\r\n")) {
			aEnviar.setText("");
			mensajes.append(user.nombre + ": " + mensaje + "\n");
			try {
				user.enviar(codChat, mensaje);
			} catch (Exception e) {
			}
		}
	}

	private Thread zumbido = new Thread() {
		public void run() {
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
				}
			}
			ventana.setLocation(x, y);
		}
	};
}
