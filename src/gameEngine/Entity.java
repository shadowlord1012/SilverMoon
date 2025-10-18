package gameEngine;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;

public class Entity {

	//Variables
	private String name;  // Name of the entity
	private Map<String,Integer> status;  // all the status for the entity
	private BufferedImage img;  // Image of the entity
	private int renderingCounter[] = {0,0,0}; // rendering counters
	private int renderSpeed = 3;  // How fast they render
	private Vector2 numberOfImages;
	private int movementSpeed;  // How fast the entity moves
	private int directionFacing = 0; // Which direction the entity is facing
	public Rectangle imgRect;  // how big the image is to be
	public Vector2 Position; // Position of the entity
	public boolean isMoving;  // is the entity Moving 
	public boolean IsActive;  // is the entity alive 
	
	//Getter Methods
	public String getName() {return name;} 
	public int getStatusByName(String value) {return status.get(value);}
	public double getWidth() {return imgRect.getWidth();}
	public double getHeight() {return imgRect.getHeight();}
	public int MovementSpeed() {return movementSpeed;}
	public boolean IsMoving() {return isMoving;}
	public int getDirectionFacing() {return directionFacing;}
	public void SetRow(int value) {renderingCounter[2] = value;}
	
	/**
	 * Creates an entity. Must be initialized 
	 */
	public Entity() {
		status = new HashMap<>();
		imgRect = new Rectangle();
	}
	
	/**
	 * Enters in all the data needed for the entity
	 * @param name
	 * @param width
	 * @param height
	 * @param movementSpeed
	 */
	public void Initialize(String name, int width, int height, int movementSpeed, int xImg, int yImg) {
		
		//Sets some of the basic values
		this.name = name;
		this.imgRect.x = 0;
		this.imgRect.y = 0;
		this.imgRect.width = width;
		this.imgRect.height = height;
		this.movementSpeed = movementSpeed;
		this.numberOfImages = new Vector2(xImg,yImg);
		
		//Sets the image
		try {
			img = ImageIO.read(new File(Global.INSTALL_LOCATION+"/Assets/Images/Entity/"+name+".jpg"));
		}catch(IOException e) { e.printStackTrace();}
		
	}
	
	/**
	 * set the status values by key eg. attack is key, 5 is value
	 * @param key
	 * @param value
	 * @return if it was completed correctly
	 */
	public boolean setStatusByPair(String key, int value) {
		
		// makes sure the value is getter then 0
		if(value >= 0) {
			// makes sure the status name is not already there
			if(!status.containsKey(key.toLowerCase())) {
				status.put(key, value);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Changes a status by key 
	 * @param key
	 * @param value
	 * @return if it was completed correctly
	 */
	public boolean changeStatusByPair(String key, int value) {
		
		//makes sure the value is getter then 0
		if(value >=0) {
			//Makes sure that status is in the map
			if(status.containsKey(key.toLowerCase())) {
				status.put(key, status.get(key)+value);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Updates the entity
	 */
	public void Update(World w) {
		
		if(this.isMoving) {
			this.renderingCounter[0]++;
			if(this.renderingCounter[0] >= renderSpeed) {
				this.renderingCounter[0] = 0;
				this.renderingCounter[1]++;
			}
			if(this.renderingCounter[1] > this.numberOfImages.X)
				this.renderingCounter[1] = 0;
		}
		else {
			this.renderingCounter[1] = 0;
		}
	}
	
	/**
	 * Draws the entity 
	 * @param gc
	 */
	public void Draw(javafx.scene.canvas.GraphicsContext gc) {
		
		if(this.IsActive)
		{
			//Changes the javax.swing buffered image to a javafx image
			javafx.scene.image.Image entityImg = SwingFXUtils.toFXImage(
					this.img.getSubimage(this.imgRect.width*this.renderingCounter[1],
							this.imgRect.height*this.renderingCounter[2],
							this.imgRect.width,
							this.imgRect.height),
							null);
			// Draws the new image on the screen
			gc.drawImage(entityImg,
					this.Position.X,
					this.Position.Y,
					this.imgRect.width*Global.SCALE,
					this.imgRect.height*Global.SCALE);
		}
	}
}
