package vistas;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import usuariosYAsistente.Usuario;

public class Chat extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private List listaConectados;
	private JTabbedPane tabChats;
	private String usuariosSeleccionados = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Cliente.main(args);
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
		this.setTitle(user);
		usuario.nuevoChat(0);
		usuario.enviar(0, "refresh");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 29, 229, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 2.0, Double.MIN_VALUE };
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
		
		new escucharCodChat_0().start();

		listaConectados.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int cant = listaConectados.getSelectedItems().length;
				String selectedItem = listaConectados.getSelectedItem();
				if (cant == 1 && !usuariosSeleccionados.contains(selectedItem + " ")) {
					try {
						usuariosSeleccionados += selectedItem + " ";
						int codChat = usuario.pedirNuevoChat(selectedItem);
							nuevaTab(selectedItem, codChat);
					} catch (Exception e1) {
					}
				}
				listaConectados.deselect(listaConectados.getSelectedIndex());
			}
		});
	}

	private void nuevaTab(String nombre, int codChat) {
		JPanel panel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{394, 0};
		gbl_panel.rowHeights = new int[]{200, 28, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);
		
		JEditorPane mensajes = new JEditorPane();
		mensajes.setContentType("text/html");
		scrollPane.setViewportView(mensajes);
		
		JEditorPane textEnviar = new JEditorPane();
		textEnviar.setContentType("text/html");
		GridBagConstraints gbc_textEnviar = new GridBagConstraints();
		gbc_textEnviar.fill = GridBagConstraints.BOTH;
		gbc_textEnviar.gridx = 0;
		gbc_textEnviar.gridy = 1;
		panel.add(textEnviar, gbc_textEnviar);
		
		tabChats.addTab(nombre, new Pestana(usuario).nuevo(codChat));	
	}

	
	class escucharCodChat_0 extends Thread {
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
					} else if (nuevo.contains("levantarConexion")
							&& !usuariosSeleccionados.contains(nuevo.substring(20) + " ")) {
						usuariosSeleccionados += nuevo.substring(20) + " ";
						nuevaTab(nuevo.substring(20), Integer.parseInt(nuevo.substring(16, 20)));
					}
				}
			} catch (Exception e) {
			}
		}
	}
}
