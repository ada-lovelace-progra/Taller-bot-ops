package plugins;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelEventos {

	private JFrame frame;

	/**
	 * Create the application.
	 * 
	 * @param texto
	 * @param hora
	 */
	public PanelEventos(String titulo, String texto, String hora) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 199, 0 };
		gridBagLayout.rowHeights = new int[] { 174, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JTextField textoEvento = new JTextField();
		textoEvento.setHorizontalAlignment(SwingConstants.CENTER);
		textoEvento.setFont(new Font("Blackadder ITC", Font.BOLD, 38));
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
		horaEvento.setHorizontalAlignment(SwingConstants.CENTER);
		horaEvento.setFont(new Font("Blackadder ITC", Font.PLAIN, 36));
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
