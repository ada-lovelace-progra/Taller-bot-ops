package tiposDeMensaje;

import java.awt.GridBagConstraints;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import java.awt.GridBagLayout;

public class TextoHtml extends JPanel {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public static GridBagConstraints gbc() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		return gbc;
	}

	public TextoHtml(String mensaje) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 118, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		setBorder(null);
		GridBagConstraints gbc_txtpnAsd = new GridBagConstraints();
		gbc_txtpnAsd.fill = GridBagConstraints.BOTH;
		gbc_txtpnAsd.gridx = 0;
		gbc_txtpnAsd.gridy = 0;
		JEditorPane contenedor = new JEditorPane();
		contenedor.setContentType("text/html");
		contenedor.setEditable(false);
		try {

			HTMLDocument doc = (HTMLDocument) contenedor.getDocument();
			HTMLEditorKit editorKit = (HTMLEditorKit) contenedor.getEditorKit();
			editorKit.insertHTML(doc, doc.getLength(), "<HTML><BODY>" + mensaje + "</BODY></HTML>", 0, 0, null);

		} catch (Exception e) {
		}
		add(contenedor, gbc_txtpnAsd);
	}
}