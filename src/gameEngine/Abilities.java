package gameEngine;

import java.awt.image.BufferedImage;

import com.google.gson.annotations.SerializedName;

public class Abilities {
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("type")
	private String type;
	
	@SerializedName("element")
	private String elementalType;
	
	@SerializedName("duration")
	private int duration;
	
	private transient Entity owner;
	private transient Vector2 position;
	private transient BufferedImage img;
	
	public Abilities() {
		
	}
	
}
