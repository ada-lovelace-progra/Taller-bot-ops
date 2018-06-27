package plugins;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class YouTube extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		WebView webview = new WebView();
		webview.getEngine().load("https://www.youtube.com/embed/yOL-EJZjmp0?autoplay=1");
		webview.setPrefSize(640, 390);
		stage.setScene(new Scene(webview));
		stage.show();
	}
	
	
}