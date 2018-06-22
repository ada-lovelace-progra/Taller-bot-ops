package vistas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.net.URL;

import javax.media.Manager;
import javax.media.Player;
import javax.swing.JFrame;

public class YouTube extends JFrame {
	private static final long serialVersionUID = 1L;

	public YouTube() {
		setLayout(new BorderLayout());
		try {
			// file you want to play
			URL mediaURL = new URL("https://www.youtube.com/watch?v=h4yVLn_djsE"); // Whatever
			// create the media player with the media url
			Player mediaPlayer;
			mediaPlayer = Manager.createRealizedPlayer(mediaURL);
			// get components for video and playback controls
			Component video = mediaPlayer.getVisualComponent();
			Component controls = mediaPlayer.getControlPanelComponent();
			add(video, BorderLayout.CENTER);
			add(controls, BorderLayout.SOUTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
