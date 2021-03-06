package vistas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import plugins.PanelEventos;
import usuariosYAsistente.Usuario;

public class Chat extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private List listaConectados;
	private JTabbedPane tabChats;
	private ArrayList<String> usuariosSeleccionados = new ArrayList<>();
	public boolean iniciado = false;
	private JButton btnNuevaSala;
	private Font fuente = new Font("Tahoma", Font.PLAIN, 11);
	public JPopupMenu popupMenu;

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
	public Chat(String user, String pass) {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setMinimumSize(new Dimension(542, 346));
		usuario = new Usuario(user, pass);

		if (user.contains("$"))
			user=user.replace("$", "");
		this.setTitle(user);

		if (!usuario.nuevoChat(0)) {
			iniciado = false;
			return;
		}

		iniciado = true;

		try {
			usuario.enviar(0, "refresh");
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 29, 22, 229, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 2.0, Double.MIN_VALUE };
		contentPane.setLayout(gridBagLayout);

		JLabel lblConectados = new JLabel("Conectados");
		GridBagConstraints gbc_lblConectados = new GridBagConstraints();
		gbc_lblConectados.insets = new Insets(0, 0, 5, 5);
		gbc_lblConectados.gridx = 0;
		gbc_lblConectados.gridy = 0;
		lblConectados.setFont(fuente);
		getContentPane().add(lblConectados, gbc_lblConectados);

		btnNuevaSala = new JButton("Nueva sala");
		btnNuevaSala.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int codChat = usuario.pedirNuevoChat("Nueva_Sala");
				nuevaTab("Nueva Sala", codChat);
			}
		});

		GridBagConstraints gbc_btnNuevaSala = new GridBagConstraints();
		gbc_btnNuevaSala.insets = new Insets(0, 0, 5, 5);
		gbc_btnNuevaSala.gridx = 0;
		gbc_btnNuevaSala.gridy = 1;
		btnNuevaSala.setFont(fuente);
		contentPane.add(btnNuevaSala, gbc_btnNuevaSala);

		listaConectados = new List();
		lblConectados.setLabelFor(listaConectados);
		GridBagConstraints gbc_listaConectados = new GridBagConstraints();
		gbc_listaConectados.insets = new Insets(0, 0, 0, 5);
		gbc_listaConectados.fill = GridBagConstraints.BOTH;
		gbc_listaConectados.gridx = 0;
		gbc_listaConectados.gridy = 2;
		getContentPane().add(listaConectados, gbc_listaConectados);

		tabChats = new JTabbedPane(JTabbedPane.TOP);

		tabChats.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W && e.isControlDown() && !e.isShiftDown() && !e.isAltDown()) {
					String user = tabChats.getTitleAt(tabChats.getSelectedIndex());
					Component selectedComponent = tabChats.getSelectedComponent();
					tabChats.remove(selectedComponent);
					usuariosSeleccionados.remove(user);
				}
			}
		});
		tabChats.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.control));
		tabChats.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tabChats.setSize(this.getSize());
		GridBagConstraints gbc_tabChats = new GridBagConstraints();
		gbc_tabChats.gridheight = 3;
		gbc_tabChats.fill = GridBagConstraints.BOTH;
		gbc_tabChats.gridx = 1;
		gbc_tabChats.gridy = 0;

		getContentPane().add(tabChats, gbc_tabChats);

		new escucharCodChat_0().start();

		listaConectados.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int cant = listaConectados.getSelectedItems().length;
				String selectedItem = listaConectados.getSelectedItem();
				if (cant == 1 && !usuariosSeleccionados.contains(selectedItem + " ")) {
					try {
						if (!selectedItem.equals("Nueva_Sala"))
							usuariosSeleccionados.add(selectedItem);
						int codChat = usuario.pedirNuevoChat(selectedItem);
						nuevaTab(selectedItem, codChat);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				listaConectados.deselect(listaConectados.getSelectedIndex());
			}
		});

	}

	private void nuevaTab(String nombre, int codChat) {
		for (int index = 0; index < tabChats.getTabCount(); index++) {
			if (tabChats.getTitleAt(index).contains(nombre)) {
				tabChats.setSelectedIndex(tabChats.indexOfTab(nombre + "*\\."));
				return;
			}
		}
		tabChats.addTab(nombre, new Pestana(usuario, tabChats, this,nombre).nuevo(codChat));
	}

	class escucharCodChat_0 extends Thread {
		public void run() {
			this.setName("EscuchaCodChat 0");
			try {
				String anterior = null;
				while (true) {
					String nuevo = usuario.recibir(0);
					if (nuevo.contains("Tenes un evento")) {
						System.out.println(nuevo);
						String[] split = nuevo.split("\\|\\|");
						new PanelEventos(split[0], split[1], split[2]);
					} else if (nuevo.contains("?")) {
						anterior = setearListaConectados(anterior, nuevo);
					} else if (nuevo.contains("levantarConexion")
							&& !usuariosSeleccionados.contains(nuevo.substring(20) + " ")) {
						usuariosSeleccionados.add(nuevo.substring(20));
						nuevaTab(nuevo.substring(20), Integer.parseInt(nuevo.substring(16, 20)));
					}

				}
			} catch (Exception e) {
			}
		}

		private String setearListaConectados(String anterior, String nuevo) {
			if (!nuevo.equals(anterior)) {
				listaConectados.removeAll();
				for (String user : nuevo.split("\\?"))
					if (!user.equals(usuario.nombre))
						listaConectados.add(user);
				anterior = nuevo;
			}
			return anterior;
		}
	}
}