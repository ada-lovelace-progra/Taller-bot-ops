package vistas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
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

public class Cliente extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	Usuario user;
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
					Cliente frame = new Cliente();
					frame.setVisible(true);
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
		registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				user = new Usuario(regUser.getText());
				contentPane.removeAll();
				try {
					sesionIniciada();
				} catch (Exception e) {
				}
			}
		});
		registrar.setBounds(260, 146, 173, 23);
		InisioSesion.add(registrar);
		InisioSesion.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { iniEmail, iniPass, iniciar, regEmail, regUser, regPass, registrar }));
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { iniEmail, iniPass, iniciar, regEmail, regUser, label, registrar }));
	}

	private void sesionIniciada() throws Exception {
		JLayeredPane Chat = new JLayeredPane();
		Chat.setBounds(0, 0, 552, 261);
		contentPane.add(Chat);

		String nombreTab = "nose";

		JList list = new JList();
		list.setBounds(10, 26, 81, 224);
		Chat.add(list);

		JLabel lblNewLabel = new JLabel("Conectados");
		lblNewLabel.setBounds(10, 0, 81, 24);
		Chat.add(lblNewLabel);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(100, 0, 460, 261);
		Chat.add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		nuevaPesatana(nombreTab, panel, 23);
	}

	private void nuevaPesatana(String nombreTab, JPanel panel, int codChat) throws UnknownHostException, Exception {
		user.nuevoChat(codChat);
		String listaMensaje = "";
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.setBounds(404, 165, 41, 68);
		panel.add(btnNewButton);

		TextArea mensajes = new TextArea();
		mensajes.setBounds(0, -1, 445, 160);
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
						String anterior = mensajes.getText();
						if (!anterior.equals("")) 
							anterior += "\n";
						
						anterior += user.recibir();
						mensajes.setText(anterior);
					} catch (Exception e) {
					}
			}
		}.start();

		tabbedPane.addTab(nombreTab, null, panel, null);
	}

	private void enviarMensaje(TextArea aEnviar, TextArea mensajes, int codChat) {
		String mensaje = aEnviar.getText();
		aEnviar.setText("");

		if (!mensaje.equals("\r\n")) {
			String anterior = mensajes.getText();
			if (!anterior.equals("")) {
				anterior += "\n";
			}
			anterior += mensaje;
			mensajes.setText(anterior);
			try {
				user.enviar(codChat, mensaje);
			} catch (Exception e) {
			}
		}
	}

}
