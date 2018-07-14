package plugins;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;

public class PanelEventos {
	
	private static final Color[] colorArray = { new Color(179, 255, 179), new Color(255, 204, 204), new Color(204, 230, 255),
			new Color(255, 255, 179), new Color(236, 179, 255), new Color(255, 204, 153), new Color(217, 255, 179),	new Color(255, 179, 204) };

	private JFrame frame;

	/**
	 * Create the application.
	 * 
	 * @param texto
	 * @param hora
	 */
	public PanelEventos(String titulo, String texto, String hora) {
		int color = (int) (Math.random()*8);
		frame = new JFrame();
		frame.getContentPane().setBackground(colorArray[color]);
		frame.setBackground(colorArray[color/2]);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 199, 0 };
		gridBagLayout.rowHeights = new int[] { 174, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JTextField textoEvento = new JTextField();
		textoEvento.setBackground(colorArray[color]);
		textoEvento.setHorizontalAlignment(SwingConstants.CENTER);
		textoEvento.setFont(new Font("Calibri", Font.BOLD, 36));
		textoEvento.setText(texto);
		textoEvento.setEditable(false);
		GridBagConstraints gbc_textoEvento = new GridBagConstraints();
		gbc_textoEvento.fill = GridBagConstraints.BOTH;
		gbc_textoEvento.insets = new Insets(0, 0, 5, 0);
		gbc_textoEvento.gridx = 0;
		gbc_textoEvento.gridy = 0;
		frame.getContentPane().add(textoEvento, gbc_textoEvento);
		textoEvento.setColumns(10);

		JTextField horaEvento = new JTextField();
		horaEvento.setBackground(colorArray[color]);
		horaEvento.setHorizontalAlignment(SwingConstants.CENTER);
		horaEvento.setFont(new Font("Calibri", Font.PLAIN, 34));
		horaEvento.setText(hora);
		horaEvento.setEditable(false);
		horaEvento.setColumns(10);
		GridBagConstraints gbc_horaEvento = new GridBagConstraints();
		gbc_horaEvento.fill = GridBagConstraints.BOTH;
		gbc_horaEvento.gridx = 0;
		gbc_horaEvento.gridy = 1;
		frame.getContentPane().add(horaEvento, gbc_horaEvento);
		frame.setTitle(titulo);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

}
