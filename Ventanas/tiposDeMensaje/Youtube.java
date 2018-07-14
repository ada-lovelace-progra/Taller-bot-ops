package tiposDeMensaje;

import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class Youtube extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Youtube(String link) {
		try {
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{54, 0};
			gridBagLayout.rowHeights = new int[]{45, 0};
			gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			setLayout(gridBagLayout);
			setAlignmentX(LEFT_ALIGNMENT);
			setSize(300, 200);
			final JFXPanel fxPanel = new JFXPanel();
			fxPanel.setAlignmentX(LEFT_ALIGNMENT);
			GridBagConstraints gbc_fxPanel = new GridBagConstraints();
			gbc_fxPanel.anchor = GridBagConstraints.NORTHWEST;
			gbc_fxPanel.gridx = 0;
			gbc_fxPanel.gridy = 0;
			add(fxPanel, gbc_fxPanel);
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
		webview.setPrefSize(320, 195);
		Scene scene = new Scene(webview);
		return (scene);
	}

	public static void main(String[] args) {
		new Youtube(
				"https://www.youtube-nocookie.com/embed/DLzxrzFCyOs?rel=0&amp;controls=0&amp;showinfo=0?autoplay=1");
	}
}