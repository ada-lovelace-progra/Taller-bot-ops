package vistas;

import java.awt.Component;
import java.awt.HeadlessException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javax.swing.JFrame;
import javafx.scene.web.WebView;

public class Youtube2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Youtube2(){ }
	
	public Youtube2( String s) throws HeadlessException {
		JFrame frame = new JFrame("RickRoll");
		final JFXPanel fxPanel = new JFXPanel();
		frame.add(fxPanel);
		frame.setSize(300, 200);
		frame.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX(fxPanel);
			}
		});
	}
	
	 static Component metodoLoco( Boolean b) {
		final JFXPanel fxPanel = new JFXPanel();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Platform.runLater(new Runnable() {
			public void run() {
				initFX(fxPanel);
			}
		});
		return fxPanel;
		
	}

	private static void initFX(JFXPanel fxPanel) {
		// This method is invoked on the JavaFX thread
		Scene scene = createScene();
		fxPanel.setScene(scene);
	}

	private static Scene createScene() {
		WebView webview = new WebView();
		webview.getEngine().load("https://www.youtube.com/embed/c2_VdzHQWNs?autoplay=1");
		webview.setPrefSize(640, 390);
		Scene scene = new Scene(webview);
		return (scene);
	}

	public static void main(String[] args) {
		new Youtube2("caca");
	}
}