package vistas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import plugins.Codificaciones;
import plugins.Zumbido;
import usuariosYAsistente.Usuario;

public class Pestana {

	private Font fuente = new Font("Tahoma", Font.PLAIN, 11);
	private Usuario usuario;
	private boolean setearonNombre = false;
	private String nombrePestana;
	private JTabbedPane tabChats;
	private int indicePestana;
	private char privacidad;
	private boolean setearonPrivacidad;
	private int mensajesSinLeer = 0;
	private Chat ventana;
	private JTextField tituloPopUp;
	private JPopupMenu popupMenu;
	private JButton botonSalir;
	private JCheckBox publicaBoolean;
	private boolean primerMensaje = true;
	private JScrollPane scrollbar;
	private int codChat;
	private static final Color[] colorArray = { new Color(255, 204, 204), // rosita
			new Color(179, 255, 179), // verdecito
			new Color(204, 230, 255), // celestito
			new Color(255, 255, 179), // amarillito
			new Color(236, 179, 255), // violetita
			new Color(255, 204, 153), new Color(217, 255, 179), // limita
			new Color(255, 179, 204) }; // morita //naranjita

	/*
	 * { Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA,
	 * Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW };
	 */

	public Pestana(Usuario usuario, JTabbedPane tabChats, Chat ventana, String nombre) {
		this.usuario = usuario;
		this.tabChats = tabChats;
		indicePestana = tabChats.getTabCount();
		this.ventana = ventana;
		this.nombrePestana = nombre;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel nuevo(int codChat) {
		this.codChat = codChat;
		JPanel panel = new JPanel();
		panel.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				mensajesSinLeer = 0;
				notificarMensajesNuevos.interrupt();
			}
		});
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 394, 0 };
		gbl_panel.rowHeights = new int[] { 200, 28, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JPanel mensajes = new JPanel();
		mensajes.setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.weighty = 1;
		scrollbar = new JScrollPane(mensajes);
		scrollbar.getVerticalScrollBar().setUnitIncrement(16);
		scrollbar.getHorizontalScrollBar().setUnitIncrement(16);
		panel.add(scrollbar, gbc);

		JScrollPane scrollEnviar = new JScrollPane();
		GridBagConstraints gbc_scrollEnviar = new GridBagConstraints();
		gbc_scrollEnviar.fill = GridBagConstraints.BOTH;
		gbc_scrollEnviar.gridx = 0;
		gbc_scrollEnviar.gridy = 1;
		scrollEnviar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollEnviar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel.add(scrollEnviar, gbc_scrollEnviar);

		JEditorPane textEnviar = new JEditorPane();
		textEnviar.setContentType("text/plain");
		textEnviar.setFont(fuente);
		scrollEnviar.setViewportView(textEnviar);

		popupMenu = new JPopupMenu();
		tituloPopUp = new JTextField();
		tituloPopUp.setFont(fuente);
		popupMenu.add(tituloPopUp);
		tituloPopUp.setColumns(10);
		publicaBoolean = new JCheckBox("Publica");
		publicaBoolean.setFont(fuente);
		popupMenu.add(publicaBoolean);
		botonSalir = new JButton("Listo");
		botonSalir.setFont(fuente);
		popupMenu.add(botonSalir);

		setearEventos(textEnviar, mensajes);
		cargaMensajesNuevosHilo(mensajes);

		refrescoDeIndice.start();

		GridBagLayout gbl_mensajes = new GridBagLayout();
		gbl_mensajes.columnWidths = new int[] { 0 };
		gbl_mensajes.rowHeights = new int[] { 0 };
		gbl_mensajes.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_mensajes.rowWeights = new double[] { Double.MIN_VALUE };
		mensajes.setLayout(gbl_mensajes);

		return panel;
	}

	public void setCodChat() {
	}

	private void setearEventos(JEditorPane textEnviar, JPanel mensajes) {

		tabChats.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON2) {
					tabChats.remove(tabChats.getSelectedIndex());
				} else if (arg0.getButton() == MouseEvent.BUTTON3) {
					popupMenu.show(tabChats, 20, 20);
					popupMenu.grabFocus();
				}
			}
		});

		textEnviar.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == KeyEvent.VK_ENTER) {
					enviarMensaje(textEnviar, mensajes);
					textEnviar.setText(null);
				} /*
					 * else if (arg0.getKeyCode() != ' ' && textEnviar.getText().length() < 5) {
					 * textEnviar.setText(textEnviar.getText().trim()); }
					 */
			}
		});

		textEnviar.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				textEnviar.setCaretPosition(textEnviar.getDocument().getLength());
			}

		});

		mensajes.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0) {
				mensajesSinLeer = 0;
				notificarMensajesNuevos.interrupt();
			}
		});

		textEnviar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				mensajesSinLeer = 0;
				notificarMensajesNuevos.interrupt();
			}
		});

		popupMenu.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				if (nombrePestana != null && nombrePestana.contains("#")) {
					// popupMenu.setVisible(true);
					tituloPopUp.setText(nombrePestana.replace("#", ""));
					publicaBoolean.setSelected(privacidad == '1');
				} else if (nombrePestana != null && !nombrePestana.contains("#"))
					popupMenu.setVisible(false);
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

		});

		botonSalir.addMouseListener(new MouseAdapter() {
			private String titulo = "";
			private String priv = "";

			@Override
			public void mouseClicked(MouseEvent e) {
				String temptitulo = "#T=" + tituloPopUp.getText();
				String temppriv = "#P=" + (publicaBoolean.isSelected() ? 1 : 0);
				try {
					if (!titulo.equals(temptitulo)) {
						titulo = temptitulo;
						setearTitulo(titulo);
						usuario.enviar(codChat, titulo);
					}
					if (!priv.equals(temppriv)) {
						priv = temppriv;
						setearPrivacidad(priv, true);
						usuario.enviar(codChat, priv);
					}
					// tituloPopUp.setText(nombrePestana.replace("#", ""));
					// publicaBoolean.setSelected(!setearonPrivacidad);
					popupMenu.setVisible(false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	private void cargaMensajesNuevosHilo(JPanel mensajes) {
		new Thread() {
			public void run() {
				this.setName("CargaMensajes");
				while (true) {
					try {
						String recibido = usuario.recibir(codChat);

						zumbido(recibido);

						if (setearonNombre && setearonPrivacidad && esInvitacion(recibido))
							recibido = codificarInvitacion(recibido);

						if (estanSeteandoElTitulo(recibido))
							setearTitulo(recibido);

						if (estanSeteandoPrivacidad(recibido))
							setearPrivacidad(recibido, false);

						// Toolkit.getDefaultToolkit().beep(); //Ruidito por default del sistema

						cargarMensaje(mensajes, recibido);
						mensajesSinLeer++;
						if (!notificarMensajesNuevos.isAlive())
							notificarMensajesNuevos.start();

						JScrollBar verticalScrollBar = scrollbar.getVerticalScrollBar();
						int maximum = verticalScrollBar.getMaximum();
						Thread.sleep(50);
						verticalScrollBar.setValue(maximum);
						verticalScrollBar.setValue(maximum + 1);
						verticalScrollBar.setValue(Integer.MAX_VALUE);
					} catch (Exception e) {
					}
				}
			}

			private void zumbido(String recibido) {
				if (recibido.contains(":zumbido:"))
					new Zumbido(ventana).start();
			}

		}.start();
	}

	private void enviarMensaje(JEditorPane textEnviar, JPanel mensajes) {
		String mensaje = textEnviar.getText().trim();
		if (mensaje.length() > 0 && !mensaje.equals("\n")) {
			textEnviar.setText("");
			mensaje = Codificaciones.codificar(mensaje);

			if (setearonPrivacidad && esInvitacion(mensaje))
				mensaje = codificarInvitacion(mensaje);

			if (estanSeteandoElTitulo(mensaje))
				setearTitulo(mensaje);

			if (setearonNombre && estanSeteandoPrivacidad(mensaje))
				setearPrivacidad(mensaje, true);

			cargarMensaje(mensajes, usuario.nombre + ": " + mensaje);

			usuario.enviar(codChat, mensaje);
		}
	}

	private void cargarMensaje(JPanel mensajes, String mensaje) {
		if (primerMensaje) {
			primerMensaje = false;
			cargarMensaje(mensajes, "");
		}
		if (!mensaje.matches("^(.*: )?#.=.*")) {
			if (mensaje.contains("@") && mensaje.contains("#"))
				mensaje = mensaje.substring(0, mensaje.indexOf("#"));

			if (mensaje.contains("meme:"))
				mensajes.add(new Mensaje().nuevo(usuario.nombre + ":"), Mensaje.gbc(), mensajes.getComponentCount());

			mensajes.add(new Mensaje().nuevo(mensaje), Mensaje.gbc(), mensajes.getComponentCount());
			mensajes.revalidate();
			mensajes.repaint();
		}
	}

	private void setearTitulo(String mensaje) {
		Matcher regex = Pattern.compile("#T=(.+)\\.?").matcher(mensaje);
		if (regex.find()) {
			setearonNombre = true;
			nombrePestana = "#" + regex.group(1);
			tabChats.setTitleAt(indicePestana, nombrePestana);
		}
	}

	private boolean estanSeteandoPrivacidad(String mensaje) {
		return mensaje.contains("#P=");
	}

	private void setearPrivacidad(String mensaje, boolean enviar) {
		Matcher regex = Pattern.compile("#P=(.)").matcher(mensaje);
		if (regex.find()) {
			setearonPrivacidad = true;
			privacidad = regex.group(1).charAt(0);

			// decirle al server que agregue la sala en caso de ser publica
			if (enviar)
				try {
					usuario.enviar(0, "agregarSala" + nombrePestana + privacidad + String.format("%04d", codChat));
				} catch (Exception e) {
					e.printStackTrace();
				}

		}
	}

	private boolean estanSeteandoElTitulo(String mensaje) {
		return mensaje.contains("#T=");
	}

	private String codificarInvitacion(String mensaje) {
		return mensaje + tabChats.getTitleAt(indicePestana) + privacidad;
	}

	private boolean esInvitacion(String mensaje) {
		return mensaje.contains("@");
	}

	private final Thread notificarMensajesNuevos = new Thread() {

		public void run() {
			this.setName("Notificacion Mensajes Nuevos |" + tabChats.getTitleAt(indicePestana));
			Color colorNotificacion = colorArray[(int) (Math.random() * 8)];
			Color color = tabChats.getBackground();
			boolean sinLeer = true;
			int noVaMas = 0;
			while (true && noVaMas != 10)
				try {
					escaneo();
					if (mensajesSinLeer != 0) {
						if (sinLeer) {
							tabChats.setBackgroundAt(indicePestana, colorNotificacion);
							sinLeer = false;
						}
						String nombre = nombrePestana + " (" + mensajesSinLeer + ")";
						tabChats.setTitleAt(indicePestana, nombre);
						Thread.sleep(500);
						tabChats.setTitleAt(indicePestana, nombrePestana + " ...");
					} else if (!sinLeer) {
						sinLeer = true;
						tabChats.setBackgroundAt(indicePestana, color);
						tabChats.setTitleAt(indicePestana, nombrePestana);
						this.interrupt();
					}
					Thread.sleep(500);
				} catch (Exception e) {
					noVaMas++;
				}
		}
	};

	private Thread refrescoDeIndice = new Thread() {
		public void run() {
			this.setName("refresco del indice");
			while (true) {
				escaneo();
				try {
					Thread.sleep(200);
				} catch (Exception e) {
				}
			}
		}

	};

	private void escaneo() {
		for (int i = 0; i < tabChats.getTabCount(); i++) {
			String tituloTemp = tabChats.getTitleAt(i);
			if (tituloTemp.matches(nombrePestana + "( \\.{3})?( \\(\\d+\\))?")) {
				indicePestana = i;
			}
		}
	}
}