package resolvedores;

import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/** 
 * Resolvedor, muestra fotos aleatoreas de 9gag.
 */
public class Gag9 extends RespuestaGenerico {

	@Override
	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return obtenerTodo();
		return null;
	}

	private String obtenerTodo() {		
			String link = null;
			try {
				link = this.extractImageUrl("https://9gag.com/random");
				//return "<img src=\"" + link + "\" height=\"50\" width=\"50\" >";
				return "<img src=\"" + link + "\">";
			} catch (IOException e) {
				e.printStackTrace();
			}
			return link;
	}
	
	
	public String extractImageUrl(String url) throws IOException {
        String contentType = new URL(url).openConnection().getContentType();
        if (contentType != null) {
            if (contentType.startsWith("image/")) {
                return url;
            }
        }
        
        Document document = Jsoup.connect(url).get();

        String imageUrl = null;

        imageUrl = getImageFromOpenGraph(document);
        if (imageUrl != null) {
            return imageUrl;
        }
        return imageUrl;
    }

    private static String getImageFromOpenGraph(Document document) {
        Element image = document.select("meta[property=og:image]").first();
        if (image != null) {
            return image.attr("abs:content");
        }
        Element secureImage = document.select("meta[property=og:image:secure]").first();
        if (secureImage != null) {
            return secureImage.attr("abs:content");
        }
        return null;
    }    

}
