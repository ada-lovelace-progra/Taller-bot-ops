package plugins;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class NotificacionSonora {
	
	public static void sonar(int nota) {
		try {
			Synthesizer midiSynth = MidiSystem.getSynthesizer();
			midiSynth.open();
			Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
			MidiChannel[] mChannels = midiSynth.getChannels();
			midiSynth.loadInstrument(instr[0]);
			mChannels[0].noteOn(nota, 100);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			mChannels[0].noteOff(nota);
		} catch (MidiUnavailableException e) {
		}
	}
	
	public static void main(String a[]) {
		sonar(60);
		sonar(60);
		sonar(90);
	}
	
}