package plugins;

import vistas.Chat;

public class Zumbido extends Thread {
	
	private Chat ventana;
	
	public Zumbido(Chat ventana) {
		this.ventana=ventana;
	}

	public void run() {
		int veces = 50;
		int x = (int) ventana.getLocation().getY();
		int y = (int) ventana.getLocation().getX();
		while (veces-- != 0) {
			int x1 = (int) (Math.random() * 20 - 10);
			int y1 = (int) (Math.random() * 20 - 10);
			ventana.setLocation(x + x1, y + y1);
			try {
				Thread.sleep(50);
			} catch (Exception e) {
			}
		}
		ventana.setLocation(x, y);
	}
}
