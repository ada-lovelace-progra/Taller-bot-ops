package vistas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

public class Cliente extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Cliente ventana;
	private Font fuente;
	private JTextField txtMailLogin;
	private JPasswordField passLogin;
	private JTextField txtMailReg;
	private JTextField txtUsuario;
	private JPasswordField passReg;

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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 466, 280);
		fuente = new Font("Tahoma", Font.PLAIN, 11);
		//setAllBounds();
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.control));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setFont(fuente);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 45, 66, 14);
		lblEmail.setFont(fuente);
		contentPane.add(lblEmail);
		
		txtMailLogin = new JTextField();
		txtMailLogin.setBounds(10, 67, 205, 20);
		txtMailLogin.setFont(fuente);
		contentPane.add(txtMailLogin);
		txtMailLogin.setColumns(10);
		
		JLabel lblContra = new JLabel("Contase\u00F1a:");
		lblContra.setBounds(10, 98, 66, 14);
		lblContra.setFont(fuente);
		contentPane.add(lblContra);
		
		passLogin = new JPasswordField();
		passLogin.setBounds(10, 116, 205, 20);
		passLogin.setFont(fuente);
		contentPane.add(passLogin);
		
		JButton btnIniciarsesion = new JButton("Iniciar sesi\u00F3n");
		btnIniciarsesion.setBounds(56, 167, 101, 23);
		btnIniciarsesion.setFont(fuente);
		contentPane.add(btnIniciarsesion);
		
		JLabel lblMailReg = new JLabel("Email:");
		lblMailReg.setBounds(238, 21, 66, 14);
		lblMailReg.setFont(fuente);
		contentPane.add(lblMailReg);
		
		txtMailReg = new JTextField();
		txtMailReg.setBounds(238, 39, 200, 20);
		txtMailReg.setFont(fuente);
		contentPane.add(txtMailReg);
		txtMailReg.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(238, 67, 66, 14);
		lblUsuario.setFont(fuente);
		contentPane.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(238, 87, 200, 20);
		txtUsuario.setFont(fuente);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasea.setBounds(238, 118, 66, 14);
		lblContrasea.setFont(fuente);
		contentPane.add(lblContrasea);
		
		passReg = new JPasswordField();
		passReg.setBounds(238, 137, 200, 20);
		passReg.setFont(fuente);
		contentPane.add(passReg);
		
		JButton btnRegistrar = new JButton("Registrarse e Iniciar sesi\u00F3n");
		btnRegistrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				iniciarSesion();
			}
		});
		btnRegistrar.setBounds(253, 183, 178, 23);
		btnRegistrar.setFont(fuente);
		contentPane.add(btnRegistrar);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(227, 0, 2, 251);
		contentPane.add(separator);
	}

	private void iniciarSesion() {
		try {
			sesionIniciada();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void sesionIniciada() throws Exception {
		
		Chat ventanaChat = new Chat(txtUsuario.getText());
		ventanaChat.setLocationRelativeTo(null);
		ventanaChat.setVisible(true);
		this.dispose();
	}
}
