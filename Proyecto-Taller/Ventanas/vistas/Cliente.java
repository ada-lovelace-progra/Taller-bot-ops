package vistas;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class Cliente extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private static Cliente ventana;
	private JPasswordField iniPass;
	private JTextField regEmail;
	private JPasswordField regPass;
	private JTextField regUser;

	private JLayeredPane inicioSesion;
	private JLayeredPane Chat;
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	private JEditorPane mensajes;
	private Font fuente;

	private JScrollPane mensajesScroll;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventana = new Cliente();
					ventana.setLocationRelativeTo(null);
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
		fuente = new Font("Tahoma", Font.PLAIN, 11);
		setAllBounds();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		iniciarSesion();
	}

	private void iniciarSesion() {
		inicioSesion = new JLayeredPane();
		contentPane.add(inicioSesion);
		inicioSesion.setLayout(null);

		iniPass = new JPasswordField();
		iniPass.setFont(fuente);
		inicioSesion.setLayer(iniPass, 0);

		inicioSesion.add(iniPass);

		JTextField iniEmail = new JTextField();
		iniEmail.setBounds(11, 24, 143, 20);
		iniEmail.setFont(fuente);
		inicioSesion.add(iniEmail);
		iniEmail.setColumns(10);

		JLabel lblUsuario = new JLabel("Email:");
		lblUsuario.setBounds(10, 11, 46, 14);
		lblUsuario.setFont(fuente);
		inicioSesion.add(lblUsuario);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(11, 56, 80, 14);
		lblContrasea.setFont(fuente);
		inicioSesion.add(lblContrasea);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(251, 0, 9, 261);
		inicioSesion.add(separator);

		regEmail = new JTextField();
		regEmail.setColumns(10);
		regEmail.setBounds(260, 24, 143, 20);
		regEmail.setFont(fuente);
		inicioSesion.add(regEmail);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(259, 11, 46, 14);
		lblEmail.setFont(fuente);
		inicioSesion.add(lblEmail);

		JLabel lblUsuario_1 = new JLabel("Usuario:");
		lblUsuario_1.setBounds(260, 56, 80, 14);
		lblUsuario_1.setFont(fuente);
		inicioSesion.add(lblUsuario_1);

		JLabel label = new JLabel("Contrase\u00F1a:");
		label.setBounds(260, 101, 80, 14);
		label.setFont(fuente);
		inicioSesion.add(label);

		regPass = new JPasswordField();
		regPass.setBounds(260, 115, 144, 20);
		regPass.setFont(fuente);
		inicioSesion.add(regPass);

		regUser = new JTextField();
		regUser.setColumns(10);
		regUser.setBounds(260, 70, 143, 20);
		regUser.setFont(fuente);
		inicioSesion.add(regUser);

		JButton iniciar = new JButton("Iniciar sesión");
		iniciar.setBounds(11, 97, 117, 23);
		iniciar.setFont(fuente);
		inicioSesion.add(iniciar);

		JButton registrar = new JButton("Registrarse e iniciar sesión");
		registrar.setBounds(260, 146, 173, 23);
		registrar.setFont(fuente);
		inicioSesion.add(registrar);

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

					if (inicioSesion != null)
						inicioSesion.setBounds((int) (0 * XRelacion), (int) (0 * YRelacion), (int) (552 * XRelacion),
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
					if (mensajesScroll!= null)
						mensajesScroll.setBounds((int) (0 * XRelacion), (int) (0 * YRelacion), (int) (445 * XRelacion),
								(int) (160 * YRelacion));

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}
			}
		}.start();
	}

	private void sesionIniciada() throws Exception {
		
		Chat ventanaChat = new Chat(regUser.getText());
		ventanaChat.setLocationRelativeTo(null);
		ventanaChat.setVisible(true);
		this.dispose();
	}

}
