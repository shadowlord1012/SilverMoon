package gameEngine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {
	
	private Clip clip;
	FloatControl fc;
	
	public Audio() {
		
	}
	public void playBGM(String name) {
		if(this.clip != null)
			stop();
		setFileBGM(name);
		fc.setValue(Global.MASTER_VOLUME);
		loop();
	}
	
	public void playSE(String name) {
		setFileSE(name);
		fc.setValue(Global.SOUND_EFFECT_VOLUME);
		play();
	}
	
	private void setFileBGM(String name) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("Resources/Audio/BGM/"+name));
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		}catch(Exception e) {e.printStackTrace();}
	}
	
	private void setFileSE(String name) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("Resources/Audio/SE/"+name));
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
