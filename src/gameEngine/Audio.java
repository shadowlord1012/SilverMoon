package gameEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {
	
	private Clip clip;
	Map<String,File> soundFiles = new HashMap<>();
	FloatControl fc;
	
	public Audio() {
		
	}
	
	public void AddSoundFile(File value, String name) {
		if(!soundFiles.containsKey(name))
			soundFiles.put(name, value);			
	}
	
	public void playBGM(String name) {
		if(this.clip != null)
			stop();
		setFile(name);
		fc.setValue(Global.MASTER_VOLUME);
		loop();
	}
	
	public void playSE(String name) {
		setFile(name);
		fc.setValue(Global.SOUND_EFFECT_VOLUME);
		play();
	}
	
	private void setFile(String name) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFiles.get(name));
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		}catch(Exception e) {e.printStackTrace();}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}
