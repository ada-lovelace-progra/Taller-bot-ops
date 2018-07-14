package tiposDeMensaje;

import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class Youtube extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Youtube(String link) {
		final JFXPanel fxPanel = new JFXPanel();
		add(fxPanel);
		setSize(300, 200);
		setVisible(true);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Scene scene = createScene(link);
				fxPanel.setScene(scene);
			}
		});
	}

	private static Scene createScene(String link) {
		WebView webview = new WebView();
		webview.getEngine().load(link);
		webview.setPrefSize(320, 195);
		Scene scene = new Scene(webview);
		return (scene);
	}

	public static void main(String[] args) {
		new Youtube("https://www.youtube.com/embed/c2_VdzHQWNs?autoplay=1");
	}
}