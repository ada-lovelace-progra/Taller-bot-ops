package tiposDeMensaje;

import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;

public class Meme extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Meme(String link) {
		try {
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{54, 0};
			gridBagLayout.rowHeights = new int[]{45, 0};
			gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			setLayout(gridBagLayout);
			setAlignmentX(LEFT_ALIGNMENT);
			setSize(200, 800);
			final JFXPanel fxPanel = new JFXPanel();
			fxPanel.setAlignmentX(LEFT_ALIGNMENT);
			GridBagConstraints gbc_fxPanel = new GridBagConstraints();
			gbc_fxPanel.anchor = GridBagConstraints.NORTHWEST;
			gbc_fxPanel.gridx = 0;
			gbc_fxPanel.gridy = 0;
			fxPanel.setBackground(Color.WHITE);
			fxPanel.setFont(new Font("Tahoma", Font.PLAIN, 11));
			add(fxPanel, gbc_fxPanel);
			setBackground(Color.WHITE);
			setVisible(true);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Scene scene = createScene(link);
					fxPanel.setScene(scene);
				}
			});
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	private static Scene createScene(String link) {
		WebView webview = new WebView();
		webview.getEngine().load(link);
		webview.setPrefSize(620, 395);
		Scene scene = new Scene(webview);
		return (scene);
	}
}