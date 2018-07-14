package vistas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import org.apache.commons.codec.digest.DigestUtils;

public class Cliente extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Cliente ventana;
	private Font fuente;
	private JTextField txtUsuario;
	private JPasswordField passReg;
	private JTextField txtMailLogin;
	private JPasswordField passLogin;

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
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setFont(fuente);

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

		passReg.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == KeyEvent.VK_ENTER)
					cargarUsuarioEIniciar();
			}
		});

		JButton btnRegistrar = new JButton("Registrarse e Iniciar sesi\u00F3n");
		btnRegistrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER)
					cargarUsuarioEIniciar();
			}
		});
		btnRegistrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cargarUsuarioEIniciar();
			}
		});
		btnRegistrar.setBounds(253, 183, 178, 23);
		btnRegistrar.setFont(fuente);
		contentPane.add(btnRegistrar);

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		panel.setBounds(0, 0, 228, 251);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblUsuario_1 = new JLabel("Usuario:");
		lblUsuario_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUsuario_1.setBounds(10, 60, 66, 14);
		panel.add(lblUsuario_1);

		txtMailLogin = new JTextField();
		txtMailLogin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtMailLogin.setColumns(10);
		txtMailLogin.setBounds(10, 82, 205, 20);
		panel.add(txtMailLogin);

		JLabel label_1 = new JLabel("Contase\u00F1a:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(10, 113, 66, 14);
		panel.add(label_1);

		passLogin = new JPasswordField();
		passLogin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		passLogin.setBounds(10, 131, 205, 20);
		panel.add(passLogin);

		JButton btnIniciarSesin = new JButton("Iniciar sesi\u00F3n");
		btnIniciarSesin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnIniciarSesin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER)
					iniciarSesion();
			}
		});
		btnIniciarSesin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				iniciarSesion();
			}
		});

		passLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == KeyEvent.VK_ENTER)
					iniciarSesion();
			}
		});

		btnIniciarSesin.setBounds(56, 182, 101, 23);
		panel.add(btnIniciarSesin);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel, txtMailLogin, passLogin, btnIniciarSesin, txtUsuario, passReg, btnRegistrar}));
	}

	@SuppressWarnings("static-access")
	private void cargarUsuarioEIniciar() {
		Chat ventanaChat = new Chat("$" + txtUsuario.getText(), hashear(passReg.getPassword()));
		if(ventanaChat.iniciado==false)
		{
			JOptionPane mensaje = new JOptionPane();
			JLabel lbl = new JLabel("No se pudo crear el usuario. Intenta nuevamente.");
			lbl.setFont(fuente);
			mensaje.showMessageDialog(ventana, lbl, "Error en la creación del usuario", JOptionPane.ERROR_MESSAGE);
		}
		else
			iniciado(ventanaChat);
	}

	@SuppressWarnings("static-access")
	private void iniciarSesion() {
		Chat ventanaChat = new Chat(txtMailLogin.getText(), hashear(passLogin.getPassword()));
		if(ventanaChat.iniciado==false)
		{
			JOptionPane mensaje = new JOptionPane();
			JLabel lbl = new JLabel("No se pudo iniciar. Intenta nuevamente.");
			lbl.setFont(fuente);
			mensaje.showMessageDialog(ventana, lbl, "Error en el inicio de sesión", JOptionPane.ERROR_MESSAGE);
		}
		else
			iniciado(ventanaChat);
	}

	private void iniciado(Chat ventanaChat) {
		if (ventanaChat.iniciado) {
			ventanaChat.setLocationRelativeTo(null);
			ventanaChat.setVisible(true);
			this.dispose();
		}
	}

	private String hashear(char[] pass) {
		String passS = "";
		for (char c : pass) {
			passS += c;
		}
		String encriptMD5 = DigestUtils.sha1Hex(passS);
		return encriptMD5;
	}

}
