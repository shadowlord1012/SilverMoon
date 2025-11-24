package gameEngine;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.gson.annotations.SerializedName;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Abilities {
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("type")
	private String type;
	
	@SerializedName("element")
	private String elementalType;
	
	@SerializedName("duration")
	private int[] duration;

	@SerializedName("cooldown")
	private int[] cooldown;
	
	@SerializedName("magicCost")
	private int magicCost;
	
	@SerializedName("damage")
	private int damage;
	
	@SerializedName("numberOfImages")
	private Vector2 numberOfImages;
	
	@SerializedName("ImageRectangle")
	private Rectangle imgRect;
	
	@SerializedName("AudioFile")
	private String audioFile;
	
	@SerializedName("requirements")
	private Requirements requirements;
	
	private int[] renderingCounter = {0,0};
	private transient Vector2 position;
	private transient BufferedImage img;
	private boolean isOnCooldown;
	private boolean isMoving;
	private boolean isActive;
	
	public String getName() { return name;}
	public String getType() { return type;}
	public String getElementalType() { return elementalType;}
	public String getAudioFile() { return audioFile;}
	public int[] getDuration() { return duration;}
	public Vector2 Position() {return position;}
	public BufferedImage getImg() {return img;}
	public void setName(String value) {name = value;}
	public void setType(String value) {type = value;}
	public void setElementalType(String value) {elementalType = value;}
	public void setAudioFile(String value) {audioFile = value;}
	public void setDuration(int[] value) {duration = value;}
	public void setPosition(Vector2 value) {position = value;}
	public boolean IsOnCoolDown() {return isOnCooldown;}
	public boolean IsActive() {return isActive;}
	
	public Abilities() {
		
	}
	
	/***
	 * Loads in the image 
	 */
	public void loadImage() {
		try {
			img = ImageIO.read(new File("Resources/Abilities/"+name+".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the ability information with the starting position that of the owner of the ability
	 * @param ownerRef
	 */
	public void Update(Entity ownerRef) {
		
		cooldown[0]++;
		if(cooldown[0] >= cooldown[1]) {
			isOnCooldown = false;
			cooldown[0] = cooldown[1];
		}
		
		if(!isMoving)
		{
			position = new Vector2(ownerRef.getPosition().X, ownerRef.getPosition().Y);
		}
		else {
			
			//increasing the rendering counter
			renderingCounter[0]++;
			
			//increasing the duration counter
			duration[0]++;
			
			//if the counter reaches a limit, it resets and adjusts the image counter
			if(renderingCounter[0] >= 3) {
				renderingCounter[0] = 0;
				renderingCounter[1]++;
			}
			
			//once the image counter reaches the max it resets to the beginning
			if(renderingCounter[1] >= numberOfImages.X-1)
				renderingCounter[1] = 0;
			
			if(duration[0] >= duration[1])
			{
				duration[0] = 0;
				isMoving = false;
				isOnCooldown = true;
				renderingCounter[0] = 0;
			}
		}
		//TODO: Add in movement code that is based off the entity class
	}
	
	public void Draw(GraphicsContext gc) {
		if(isMoving && img != null) {
			Image fxImg = SwingFXUtils.toFXImage(
					img.getSubimage(
						imgRect.width*renderingCounter[1], 
						0, imgRect.width, imgRect.height), 
						null);
			
			gc.drawImage(fxImg, 
					position.X, 
					position.Y,
					imgRect.width*Global.SCALE,
					imgRect.height*Global.SCALE
					);
		}
		
	}
}
