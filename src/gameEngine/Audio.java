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
	
	/* * Play Background Music
	 */
	public void playBGM(String name) {
		
		//Stop current BGM if playing
		if(clip != null)
			stop();
		
		//Set and play new BGM
		setFileBGM(name);
		
		//Set volume
		fc.setValue(Global.MASTER_VOLUME);
		
		// Play and loop
		loop();
	}
	/* * Play Sound Effect
	 */
	public void playSE(String name) {
		
		//Stop current SE if playing
		setFileSE(name);
		
		//Set volume
		fc.setValue(Global.SOUND_EFFECT_VOLUME);
		
		// Play once
		play();
	}
	
	/* * Set Audio File for BGM
	 */
	private void setFileBGM(String name) {
		try {
			//Load Audio File
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("Resources/Audio/BGM/"+name));
			
			//Create Clip
			clip = AudioSystem.getClip();
			
			//Open Clip
			clip.open(ais);
			
			//Get Volume Control
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			
		}catch(Exception e) {e.printStackTrace();}
	}
	
	/* * Set Audio File for SE
	 */
	private void setFileSE(String name) {
		try {
			//Load Audio File
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("Resources/Audio/SE/"+name));
			
			//Create Clip
			clip = AudioSystem.getClip();
			
			//Open Clip
			clip.open(ais);
			
			//Get Volume Control
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		}catch(Exception e) {e.printStackTrace();}
	}
	
	/** * Play Audio Clip
	 */
	public void play() {
		clip.start();
	}
	/**
	 * Loop Audio Clip
	 */
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/** * Stop Audio Clip
	 */
	public void stop() {
		clip.stop();
	}
	
	/** * Shutdown Audio System
	 */
	public void shutdown() {
		clip.close();
	}
}
