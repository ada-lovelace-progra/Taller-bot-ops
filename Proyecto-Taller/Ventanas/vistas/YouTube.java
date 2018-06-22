package vistas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.net.URL;

import javax.media.Manager;
import javax.swing.JFrame;

public class YouTube extends JFrame
{
	private static final long serialVersionUID = 1L;

	public YouTube()
    {
        setLayout(new BorderLayout());

        //file you want to play
        URL mediaURL = //Whatever
        //create the media player with the media url
        Player mediaPlayer = Manager.createRealizedPlayer(mediaURL);
        //get components for video and playback controls
        Component video = mediaPlayer.getVisualComponent();
        Component controls = mediaPlayer.getControlPanelComponent();
        add(video,BorderLayout.CENTER);
        add(controls,BorderLayout.SOUTH);
    }
}

