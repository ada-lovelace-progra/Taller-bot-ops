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
import javax.swing.JLabel;
import usuariosYAsistente.Usuario;


public class Cliente extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;

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

		JList list = new JList();
		list.setBounds(10, 29, 81, 224);
		contentPane.add(list);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(101, 0, 460, 261);
		contentPane.add(tabbedPane);

		nuevaPestana(tabbedPane);
		nuevaPestana(tabbedPane);
		nuevaPestana(tabbedPane);

		JLabel lblNewLabel = new JLabel("Conectados");
		lblNewLabel.setBounds(10, 4, 81, 24);
		contentPane.add(lblNewLabel);
	}

	protected void nuevaPestana(JTabbedPane tabbedPane) {
		JPanel panel = new JPanel();
		panel.setLayout(null);

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
					enviarMensaje(aEnviar, mensajes);
				else if (aEnviar.getText().startsWith("\r\n"))
					aEnviar.setText(aEnviar.getText().substring(2));
			}
		});

		aEnviar.setBounds(0, 165, 398, 68);
		panel.add(aEnviar);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enviarMensaje(aEnviar, mensajes);
			}
		});

		tabbedPane.addTab("New tab", null, panel, null);
	}

	private void enviarMensaje(TextArea aEnviar, TextArea mensajes) {
		String text = aEnviar.getText();
		aEnviar.setText("");
		if (!text.equals("\r\n")) {
			String anterior = mensajes.getText() + "\n";
			mensajes.setText((anterior.length() > 2 ? anterior : "") + text);
		}
	}
}
